PrependTo[$Path,"../../../paperProduction/mathAMA/AMAModel/"];
PrependTo[$Path,"../../../mathAMA/NumericAMA"];
PrependTo[$Path,"../../../mathAMA/SymbolicAMA"];
PrependTo[$Path,"../../../mathSmolyak/mathSmolyak/"];
PrependTo[$Path,"../../../ProtectedSymbols"];
PrependTo[$Path,"../../../AMASeriesRepresentation/AMASeriesRepresentation"];
Print["reading christianoFisher.m"]
BeginPackage["christianoFisher`",{(*"occBindRecur`",*)"ProtectedSymbols`","AMAModel`","SymbolicAMA`","NumericAMA`"(*,"ProjectionInterface`"*)}]

cc::usage="rbc model variable"
kk::usage="rbc model variable"
theta::usage="rbc model variable"
alpha::usage="rbc model parameter"
delta::usage="rbc model parameter"
compCon::usage="compCon[aPath_?MatrixQ]:=Function[{aPath,theZs}"
stateSel::usage="stateSel[aPath_?MatrixQ]:=Function[{aPath}"
rbcEqns::usage="rbc model equations"
getRBCFixedPoint::usage="getRBCFixedPoint"
condExp::usage="condExp[kktm1_?NumberQ,ii_Integer]"
compZs::usage="compZs[hmatNum_?MatrixQ,kktm1_?NumberQ,ii_Integer]"

Begin["Private`"]

(*JEDC 2000*)
rbcEqns={
 1/cc[t]-(delta*((E^eps[theta][t])*(1/cc[t+1])*((alpha *(kk[t]^(alpha-1)) )))),
cc[t] + E^(kk[t])-((E^eps[theta][t])*(kk[t-1]^alpha))
}
(*parameters page 21 using state 1*)
paramSubs={
alpha->36/100,
delta->95/100
} 






If[Length[cSSSub]===0,
Print["computing steady state subs"];
rbcSSEqns=Thread[(rbcEqns//.{eps[_][_]->0,xx_[t+_.]->xx})==0];
kSSSub=PowerExpand[Simplify[Solve[delta*a\lpha*kk^alpha==kk,{kk},Reals],(0<alpha<1)&&(0<delta<1)][[2]]];
cSSSub=Flatten[Solve[Simplify[rbcSSEqns/.kSSSub][[2]],cc]];
ssSolnSubs=Join[cSSSub,kSSSub]]


hmatSymbRaw=(((equationsToMatrix[
rbcEqns/.{eps[_][_]->0}]//FullSimplify)/.{xx_[t+_.]->xx})/.ssSolnSubs)//FullSimplify;
forSubs={alpha^(1 - alpha)^(-1)*delta^(1 - alpha)^(-1)}
simpSubs=Thread[forSubs->nu];
forParamSubs=Thread[nu->forSubs]//.paramSubs
tog=Join[paramSubs,forParamSubs]
rbcSimp=(rbcEqns)/.tog

psiepsSymb=Transpose[{((D[#,eps[Private`theta][t]]&/@ rbcSimp)/.{eps[_][_]->0,xx_[t+_.]->xx})/.ssSolnSubs}]
psieps=psiepsSymb//.tog;

theVal=Inverse[IdentityMatrix[2]-fmat] . phimat . psic 

psiz=IdentityMatrix[2]


hmatSymb=hmatSymbRaw/.simpSubs
hSum=hmatSymb[[All,Range[2]]]+hmatSymb[[All,2+Range[2]]]+hmatSymb[[All,4+Range[2]]];
ssSolnVec={{cc},{kk}}/.ssSolnSubs;
psicSymb=hSum . ssSolnVec;
psic=psicSymb//.tog



{zfSymb,hfSymb}=symbolicAR[hmatSymb];
amatSymb=symbolicTransitionMatrix[hfSymb];
{evlsSymb,evcsSymb}=Eigensystem[Transpose[amatSymb]];
qmatSymb=Join[zfSymb,evcsSymb[[{4}]]];
Print["computing and simplifying the symbolic b phi f etc"]
{bmatSymb,phimatSymb,fmatSymb}=symbolicComputeBPhiF[hmatSymb,qmatSymb]//Simplify;
hmat=hmatSymb//.tog;
bmat=bmatSymb//.tog;
phimat=phimatSymb//.tog;
fmat=fmatSymb//.tog;


Print["defining constraint and selector funcs"]


compCon={
Function[{aPath,theZs},0==rbcSimp[[1]]/.{
kk[t-1]->aPath[[2,1]],kk[t]->aPath[[4,1]],
cc[t]->aPath[[3,1]],cc[t+1]->aPath[[5,1]],eps[theta][t]->eps}],
Function[{aPath,theZs},0==rbcSimp[[2]]/.{
kk[t-1]->aPath[[2,1]],kk[t]->aPath[[4,1]],
cc[t]->aPath[[3,1]],cc[t+1]->aPath[[5,1]],eps[theta][t]->eps}]}


getRBCFixedPoint[fpTarget_List,theSys_Function,initGuess_List]:=
With[{theSysRidT=theSys/.xx_[t]->xx,
targetRidT=fpTarget/.xx_[t]->xx},(*Print["theRysRidT=",theSysRidT];*)
FixedPoint[targetRidT/.With[{soln=
Flatten[FindRoot[(theSysRidT @@ #),{#,.18}&/@targetRidT]]},(*Print["soln=",{#,soln,targetRidT,theSysRidT//InputForm}];*)
If[Not[MatchQ[soln,{(_->_)..}]],Throw[{"NSolve Failed in >fpForInitState for",{#//InputForm,theSysRidT//InputForm,targetRidT,Stack[]}}],soln]]&,initGuess,SameTest->mySameQ]//Chop]



stateSel={2}
(*always used with epsVal=0*)
noCnstrnGuess= With[{linPFSys=
Flatten[bmat . {{0},{kVal}}+phimat . (psieps *0+psic)]//.paramSubs},
{Function @@ {{kVal},linPFSys[[2]]}}]

(*/.paramSubs//myN;*)

(*
futDiffDet[ii_Integer]:=
With[{fkktm1=Nest[((alpha*delta)*kktm1^alpha//.(tog//N)) *#&,1,ii]},
With[{fkkt=(alpha*delta)*fkktm1^alpha},
With[{fcct=fkktm1^alpha-fkkt,fkktp1=(alpha*delta)*fkkt^alpha},
With[{fcctp1=fkkt^alpha-fkktp1},
With[{theVec=Transpose[{{fcctm1,fkktm1,fcct,fkkt,fcctp1,fkktp1}}]//.tog//N,
theSymbs=Transpose[{{cc[t-1],kk[t-1],cc[t],kk[t],cc[t+1],kk[t+1]}}]},
With[{theSubs=Thread[Flatten[theSymbs]->Flatten[theVec]]},
{theVec,(rbcEqns/.eps[theta][t]->0)//.theSubs,
hmatSymb . theVec//.tog}
//Simplify]]]]]]
*)

prodTermPre=(alpha*delta)//.(tog)//N;
prodTermPost=alpha//.(tog)//N;
expVal=alpha//.tog//N;
forCompile={cc/.ssSolnSubs/.paramSubs}//N;

condExp=
Compile[{{kktm1,_Real},{epsVal,_Real},{ii,_Integer}},
With[{kkVals=Drop[NestList[epsVal*prodTermPre*#^prodTermPost&,kktm1,ii],0]},
With[{yyVals=epsVal*#^expVal&/@kkVals},
With[{ccVals=Drop[yyVals,-1]-Drop[kkVals,1]},
With[{thetransp=Transpose[{Riffle[Flatten[ccVals],Flatten[Drop[kkVals,1]]]}]},
Join[{forCompile,{kktm1}},thetransp]]]]]]


compZs=Compile[{{tryMat,_Real,2},{kktm1,_Real},{epsVal,_Real},{ii,_Integer}},
With[{thePath=Flatten[condExp[kktm1,epsVal,ii+2]]},
Table[(tryMat . Transpose[{thePath[[jj*2+Range[6]]]}]),{jj,0,ii-1}]]]






rbcEqnsApply[{ctm1_,ktm1_,ct_,kt_,ctp1_,ktp1_}]:=
rbcEqns/.eps[theta][t]->0/.{cc[t]->ct,cc[t+1]->ctp1,kk[t-1]->ktm1,kk[t]->kt}//.tog





End[]
EndPackage[]
Print["done reading christianoFisher.m"]

