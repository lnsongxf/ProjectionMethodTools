package gov.frb.ma.msu.ProjectionMethodToolsJava;

import gov.frb.ma.msu.ProjectionMethodToolsJava.DoEqns;
import gov.frb.ma.msu.ProjectionMethodToolsJava.EquationValDrv;
import gov.frb.ma.msu.ProjectionMethodToolsJava.ProjectionRuntimeException;
import gov.frb.ma.msu.ProjectionMethodToolsJava.StateVarTime;
import gov.frb.ma.msu.ProjectionMethodToolsJava.StochasticBasis;

public class doSingEqnDivideSclr extends DoEqns {
	public 
	void updateParams(double [] paramVec){};
    public EquationValDrv updateValDrv(StochasticBasis theProjModel) throws ProjectionRuntimeException{
	StateVarTime kVT;
kVT=new StateVarTime("kk",-1);
//eqValDrv km1=kVT.evalVar(theCollocationSolution);
kVT=new StateVarTime("kk",0);
EquationValDrv k0 = kVT.evalVar(theProjModel);
kVT=new StateVarTime("kk",1);
//eqValDrv kp1 = kVT.evalVar(theCollocationSolution);
/*
kVT=new StateVarTime("aName1",0);
eqValDrv an10 =kVT.evalVar(theCollocationSolution);
kVT=new StateVarTime("aName1",1);
eqValDrv an1p1 =kVT.evalVar(theCollocationSolution);
*/
EquationValDrv k0t0 = k0.times(k0).divide(2.).minus(.5);
return(k0t0);
    }
    public	double [] getParams(){return (new double[0]); };
}
