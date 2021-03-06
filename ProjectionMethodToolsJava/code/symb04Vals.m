BeginPackage["symb04Vals`",{"labDocPrep`","ProjectionInterface`"}]
Print["reading symb04 package"]
(*compute z0 for time zero constraint only *)
Print["for three"]


aPath04Vals=genPath[04];


try04Vals={
((aPath04Vals[[5,1]]>=0.02&&Global`zzz$3$1[Global`t]==0)||
(aPath04Vals[[5,1]]==0.02&&Global`zzz$3$1[Global`t]>=0))&&
((aPath04Vals[[8,1]]>=0.02&&Global`zzz$2$1[Global`t]==0)||
(aPath04Vals[[8,1]]==0.02&&Global`zzz$2$1[Global`t]>=0))&&
((aPath04Vals[[11,1]]>=0.02&&Global`zzz$1$1[Global`t]==0)||
(aPath04Vals[[11,1]]==0.02&&Global`zzz$1$1[Global`t]>=0))&&
((aPath04Vals[[14,1]]>=0.02&&Global`zzz$0$1[Global`t]==0)||
(aPath04Vals[[14,1]]==0.02&&Global`zzz$0$1[Global`t]>=0))
}

try04ValsFunc=Function @@ {{Global`qtm1,Global`rutm1,Global`eps},try04Vals}
try04ValsVals[Global`qtm1_?NumberQ,Global`rutm1_?NumberQ,Global`eps_?NumberQ]:=
{Global`zzz$0$1[Global`t],Global`zzz$1$1[Global`t],Global`zzz$2$1[Global`t],Global`zzz$3$1[Global`t]}/.Flatten[Solve[try04ValsFunc[Global`qtm1,Global`rutm1,Global`eps],
{Global`zzz$3$1[Global`t],Global`zzz$2$1[Global`t],Global`zzz$1$1[Global`t],Global`zzz$0$1[Global`t]}]]


try04Valsq1r1Vals[qtm1Arg_?NumberQ,rutm1Arg_?NumberQ,epsArg_?NumberQ]:=
(aPath04Vals[[{7,9},1]]/.Flatten[Solve[try04ValsFunc[qtm1Arg,rutm1Arg,epsArg],
{Global`zzz$2$1[Global`t],Global`zzz$1$1[Global`t],Global`zzz$0$1[Global`t],Global`zzz$3$1[Global`t]}]])/.{Global`qtm1->qtm1Arg,Global`rutm1->rutm1Arg,Global`eps->epsArg}

aPath04ValsExtFunc[qtm1Arg_?NumberQ,rutm1Arg_?NumberQ,epsArg_?NumberQ]:=  
With[{tp=genPath[3,1]/.{Global`qtm1->qtm1Arg,Global`rutm1->rutm1Arg,Global`eps->epsArg},
tVals=try04ValsVals[qtm1Arg,rutm1Arg,epsArg]},
tp/.{Global`zzz$0$1[Global`t]:>tVals[[1]],
Global`zzz$1$1[Global`t]:>tVals[[2]],
Global`zzz$2$1[Global`t]:>tVals[[3]],
Global`zzz$3$1[Global`t]:>tVals[[4]]
}]

hmatApp04Vals[Global`qtm1_?NumberQ,Global`rutm1_?NumberQ,Global`eps_?NumberQ]:=
With[{tp=aPath04ValsExtFunc[Global`qtm1,Global`rutm1,Global`eps]},
Join[Global`hmat.tp[[Range[9]]],
Global`hmat.tp[[Range[9]+3]],
Global`hmat.tp[[Range[9]+6]]
]//Chop]

igVar=Unique[];
{symb04ValsFirstSecs,igVar}=Timing[try04ValsVals[-.1,-.1,0]];
{symb04ValsSecondSecs,igVar}=Timing[try04ValsVals[-.1,-.1,0]];
Splice["symb04ValsSecs.mtex"]

EndPackage[]
Print["done reading symb04Vals package"]
