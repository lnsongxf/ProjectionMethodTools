BeginPackage["symb02ValsRec`",{"labDocPrep`","ProjectionInterface`","symb01ValsRec`"}]
Print["reading symb02 package"]
(*compute z0 for time zero constraint only *)
Print["for 02"]


aPath02ValsRec=genPath[02];

(*
try02ValsRec={
((aPath02ValsRec[[5,1]]>=0.02&&Global`zzz$1$1[Global`t]==0)||
(aPath02ValsRec[[5,1]]==0.02&&Global`zzz$1$1[Global`t]>=0))&&
Global`qq[Global`t]==aPath02ValsRec[[4,1]]&&
Global`ru[Global`t]==aPath02ValsRec[[6,1]]&&
Global`zzz$0$1[Global`t]==
Global`zzz$0$1Func[Global`qq[Global`t],Global`ru[Global`t],0]
}
*)
try02ValsRec={
((aPath02ValsRec[[5,1]]>=0.02&&Global`zzz$1$1[Global`t]==0)||
(aPath02ValsRec[[5,1]]==0.02&&Global`zzz$1$1[Global`t]>=0))&&
Global`zzz$0$1[Global`t]==
Global`zzz$0$1InterpFunc[aPath02ValsRec[[4,1]],aPath02ValsRec[[6,1]],0]
}

useVal[
Function @@ 
{{Global`qtm1,Global`rutm1,Global`eps},
aPath02ValsRec/.

delFuncSub[(xx_InterpolatingFunction|xx_Function)]:=
Global`zzz$1$1[Global`t]->
((Function @@ {{Global`qtm1Arg,Global`rutm1Arg,Global`epsArg},
xx[Global`qtm1Arg,Global`rutm1Arg,Global`epsArg] +Global`zzz$0$1InterpFunc[Global`qtm1Arg,Global`rutm1Arg,Global`epsArg]})[Global`qtm1,Global`rutm1,Global`eps])



(*
slick=({
((aPath02ValsRec[[5,1]]>=0.02&&Global`zzz$1$1[Global`t]==0)||
(aPath02ValsRec[[5,1]]==0.02&&Global`zzz$1$1[Global`t]>=0))}/.{
Global`zzz$0$1[Global`t]->
Global`zzz$0$1InterpFunc[aPath02ValsRec[[4,1]],aPath02ValsRec[[6,1]],0]})//.{
Global`zzz$1$1[Global`t]->
Global`delZ+Global`zzz$0$1InterpFunc[aPath02ValsRec[[1,1]],aPath02ValsRec[[3,1]],0]}
*)

FindRoot[
(Global`zzz$0$1[t]==
Global`zzz$0$1InterpFunc[aPath02ValsRec[[4,1]],aPath02ValsRec[[6,1]],0]/.
delFuncSub[Function[{xx,yy,zz},0]])/.{Global`qtm1->-.1,Global`rutm1->-.08,Global`eps->0}/.{Global`qtm1->-.1,Global`rutm1->-.08,Global`eps->0},{Global`zzz$0$1[t],0}]



(*Global`zzz$0$1InterpFunc[Global`qq[Global`t],Global`ru[Global`t],0]*)

vars02=Cases[Variables[Level[try02ValsRec,{-2}]],_[Global`t]]

(*
try02ValsRecFunc=Function @@ {{Global`qtm1,Global`rutm1,Global`eps},try02ValsRec}
try02ValsRecVals[Global`qtm1_?NumberQ,Global`rutm1_?NumberQ,Global`eps_?NumberQ]:=
vars02/.Flatten[Solve[try02ValsRecFunc[Global`qtm1,Global`rutm1,Global`eps],vars02,Reals]]


try02ValsRecq1r1Vals[qtm1Arg_?NumberQ,rutm1Arg_?NumberQ,epsArg_?NumberQ]:=
(aPath02ValsRec[[{7,9},1]]/.Flatten[Solve[try02ValsRecFunc[qtm1Arg,rutm1Arg,epsArg],
vars02]])/.{Global`qtm1->qtm1Arg,Global`rutm1->rutm1Arg,Global`eps->epsArg}

aPath02ValsRecExtFunc[qtm1Arg_?NumberQ,rutm1Arg_?NumberQ,epsArg_?NumberQ]:=  
With[{tp=genPath[02,1]/.{Global`qtm1->qtm1Arg,Global`rutm1->rutm1Arg,Global`eps->epsArg},
tVals=try02ValsRecVals[qtm1Arg,rutm1Arg,epsArg]},
tp/.Thread[vars02->tVals]]

hmatApp02ValsRec[Global`qtm1_?NumberQ,Global`rutm1_?NumberQ,Global`eps_?NumberQ]:=
With[{tp=aPath02ValsRecExtFunc[Global`qtm1,Global`rutm1,Global`eps]},
Join[Global`hmat.tp[[Range[9]]],
Global`hmat.tp[[Range[9]+3]],
Global`hmat.tp[[Range[9]+6]]
]//Chop]
(*
igVar=Unique[];
Print["symb02ValsRec first timing"]
{symb02ValsRecFirstSecs,igVar}=Timing[try02ValsRecVals[-.1,-.1,0]];
Print["symb02ValsRec second timing"]
{symb02ValsRecSecondSecs,igVar}=Timing[try02ValsRecVals[-.1,-.1,0]];
Splice["symb02ValsRecSecs.mtex"]

*)
Print["construct pre interp functions"]
Global`zzz$1$1PreInterpFunc=Function[{Global`qqVar,Global`ruVar,Global`epsVar},
(Global`zzz$0$1[Global`t]+Global`delZ)/.Flatten[
Solve[try02ValsRec/.Global`zzz$1$1[Global`t]->(Global`delZ+Global`zzz$0$1InterpFunc[Global`qtm1,Global`rutm1,Global`eps])/.
{Global`qtm1->Global`qqVar,Global`rutm1->Global`ruVar,Global`eps->Global`epsVar},{Global`zzz$0$1[Global`t],Global`delZ},Reals]]]

Print["construct pre interp functions"]
Global`chkZzz$1$1PreInterpFunc=Function[{Global`qqVar,Global`ruVar,Global`epsVar},
(Global`zzz$0$1[Global`t]+Global`delZ)/.Flatten[
Solve[try02ValsRec/.Global`zzz$1$1[Global`t]->(Global`delZ+Global`zzz$0$1PreInterpFunc[Global`qtm1,Global`rutm1,Global`eps])/.
{Global`qtm1->Global`qqVar,Global`rutm1->Global`ruVar,Global`eps->Global`epsVar},{Global`zzz$0$1[Global`t],Global`delZ},Reals]]]


Print["construct interpolation"]
Global`zzz$1$1InterpFunc=Global`makeInterpFunc[Global`zzz$1$1PreInterpFunc]
*)

EndPackage[]
Print["done reading symb02ValsRec package"]


