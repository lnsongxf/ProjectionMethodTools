



BeginPackage["symb02`",{"labDocPrep`","ProjectionInterface`"}]
Print["reading symb02 package"]
(*compute z0 for time zero constraint only *)
Print["for two"]

aPath02=genPath[2]
try02={
((aPath02[[5,1]]>=0.02&&Global`zzz$1$1[Global`t]==0)||
(aPath02[[5,1]]==0.02&&Global`zzz$1$1[Global`t]>=0))&&
((aPath02[[8,1]]>=0.02&&Global`zzz$0$1[Global`t]==0)||
(aPath02[[8,1]]==0.02&&Global`zzz$0$1[Global`t]>=0))
}
Export["prettyEqns02A.pdf",
MatrixForm[(try02[[1,1]]//FullSimplify)//. Global`latexSubs]]
Export["prettyEqns02B.pdf",
MatrixForm[(try02[[1,2]]//FullSimplify)//. Global`latexSubs]]


try02Func=Function @@ {{Global`qtm1,Global`rutm1,Global`eps},try02}

{symb02FirstSecs,ig02}=Timing[soln02=Flatten[Solve[try02Func[Global`qtm1,Global`rutm1,Global`eps],{Global`zzz$1$1[Global`t],Global`zzz$0$1[Global`t]},Reals]]];
{symb02SecondSecs,ig02}=Timing[soln02=Flatten[Solve[try02Func[Global`qtm1,Global`rutm1,Global`eps],{Global`zzz$1$1[Global`t],Global`zzz$0$1[Global`t]},Reals]]];
condExps=DeleteCases[Global`zzz$1$1[Global`t]/.#&/@soln02,Global`zzz$1$1[Global`t]]
pw=Piecewise[((List @@ #)//Expand//Simplify)&/@condExps]//FullSimplify
try02ValsSoln= Function @@ {{Global`qtm1,Global`rutm1,Global`eps},pw}
Splice["symb02Secs.mtex"]




(*
Get["symb02Other.mth"]
*)


EndPackage[]
Print["done reading symb02 package"]
