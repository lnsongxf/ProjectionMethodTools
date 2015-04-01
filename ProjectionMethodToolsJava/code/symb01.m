BeginPackage["symb01`",{"labDocPrep`","ProjectionInterface`"}]
Print["reading symb01 package"]

(*compute z0 for time zero constraint only *)

Print["for one"]

aPath01=genPath[1]

try01={
(aPath01[[5,1]]>=0.02&&Global`zzz$0$1[Global`t]==0)||
(aPath01[[5,1]]==0.02&&Global`zzz$0$1[Global`t]>=0)
}
Export["try01A.pdf",try01[[1,1]]]
Export["try01B.pdf",try01[[1,2]]]

{symb01FirstSecs,ig01}=Timing[slv01=Solve[try01,{Global`zzz$0$1[Global`t]},Reals]//FullSimplify//Chop]
{symb01SecondSecs,ig01}=Timing[slv01=Solve[try01,{Global`zzz$0$1[Global`t]},Reals]//FullSimplify//Chop]
Global`zzz$0$1Func= Function @@ {{Global`qtm1,Global`rutm1,Global`eps},Piecewise[List @@@ (Last/@Flatten[slv01])]}

Splice["symb01Secs.mtex"]

Print["presentation computions for one period"]
red01=Reduce[try01,{Global`zzz$0$1[Global`t]},Reals]//FullSimplify//Chop
Export["red01A.pdf",red01[[1,1]]]
Export["red01B.pdf",red01[[1,2]]]
Export["red01C.pdf",red01[[2]]]

hmatApp01NotFunc=Global`hmat .(aPath01Ext=genPath[1,1]/.Global`zzz$0$1[Global`t]->Global`zzz$0$1Func[Global`qtm1,Global`rutm1,Global`eps]//FullSimplify//Chop)//FullSimplify//Chop
Export["prettyhmatApp01.pdf",MatrixForm[hmatApp01NotFunc//.Global`latexSubs/.Global`morePaperSubs]]
Export["prettyPath01.pdf",MatrixForm[aPath01Ext//.Global`latexSubs/.Global`morePaperSubs]]

Export["prettyEqns01.pdf",
MatrixForm[(Transpose[{eqns01=doRecurIneqOccEqns[{}]/.{Global`eqvdIf->If,GreaterEqual->Greater}}]//Simplify)//.Global`latexSubs]
]
(*change conditionalExpression of two cases to form to get to piecewise*)
pairs=Partition[Sort[Flatten[
Solve[Thread[eqns01==0],{Global`qq[Global`t],Global`ru[Global`t],Global`rr[Global`t],Global`discrep[Global`t],Global`zzz$0$1[Global`t]},Reals]]],2]


soln01=(Piecewise[List @@@ Last /@ #]//FullSimplify//Chop)&/@pairs

Export["prettySoln01Q.pdf", MatrixForm[soln01[[2]]//Simplify/.Global`latexSubs]]
Export["prettySoln01R.pdf", MatrixForm[soln01[[3]]//Simplify/.Global`latexSubs]]
Export["prettySoln01Z.pdf", MatrixForm[soln01[[5]]//Simplify/.Global`latexSubs]]



EndPackage[]
Print["done reading symb01 package"]