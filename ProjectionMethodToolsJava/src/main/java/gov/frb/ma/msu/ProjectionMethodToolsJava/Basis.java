

package gov.frb.ma.msu.ProjectionMethodToolsJava;

import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

/**
 * Class contains information characterizing the orthogonal 
 * basis for the projection. 
 * 
 * @author Gary S Anderson (m1gsa00)
 * 
 * @version 1.0
 *
 */


public abstract class Basis {
	/**
	 * Orthogonal polynomials associated with state variables
	 */
	private StateVariablePolynomials theState;
	/**
	 * Orthogonal polynomials associated with non-state variables
	 */
	private NonStateVariablePolynomials theNonState;

	/**
	 * Will contain information describing the Newton iteration history
	 *  for solving
	 * the polynomial collocation equation system
	 */
	List<NewtonIterInfo> newtonIters = new ArrayList<NewtonIterInfo>();

	/**
	 * The shrinkage applied to the newton step if newton step produces 
	 * a NaN
	 * when solving the collocation equation system 
	 */
	private double shrinkFactor = .1;
	/**
	 * Maximumum number of shrinkage steps allowed for Newton step when solving
	 * collocation equation system
	 */
	private int maxShrink = 20;
	/**
	 * The number of newton steps so far (this may now really be in newtonIters)
	 */
	private int newtonSteps = 0;


	/**
	 * gets the variable index for the variable with the given lag or lead
	 * as specified in varTime
	 * @param theVar characterized variable name and lag/lead time index
	 * @return int the variable index
	 * @throws ProjectionRuntimeException
	 */
	public int getVarIndex(VarTime theVar) throws ProjectionRuntimeException {
		int theRes;
		String varName=theVar.getVarName();
		if(getTheState().getTheGrid().hasVarQ(theVar)) {	    
			theRes=getTheState().getTheGrid().getVarIndex(varName);} else
			{theRes=getTheNonState().varPositionNSP(varName);}
		return(theRes);
	}


	public Matrix getDrvPolyNowValMatrix(int varNum, int dVarNum, int numNodes) {
		int ii;
		int colDim=getTheState().getTheGrid().powersPlusOneProd();
		double [][] valArray=new double[numNodes][1];

		Matrix rightRow = 
			getTheNonState().getRelevantWeightsNSP().getMatrix(varNum,
					varNum,0,colDim-1);
		Matrix[] correctDrvVals=getTheState().getChebPolyDrvsWRTxTimeTp1Correct();
		for(ii=0;ii<numNodes;ii++){

			valArray[ii][0]=rightRow.times(correctDrvVals[ii]).get(0,dVarNum);
		}
		return(new Matrix(valArray));

	}



	public Matrix getDrvPolyNowDrvMatrix(int varNum, int dVarNum, int numNodes) {
		int ii;int jj;

		int wtsColDim=getTheState().getTheGrid().powersPlusOneProd();
		int numCols=getTheState().getTheGrid().powersPlusOneProd()*(
				getTheState().getTheGrid().getStateDimWithoutShocks()+
				getTheNonState().getNonStateVarDim());
		int numStates=getTheState().getTheGrid().getStateDimWithoutShocks();
		double [][] valArray=new double[numNodes][numCols];
		Matrix[] correctDrvVals=getTheState().getChebPolyDrvsWRTxTimeTp1Correct();
		for(ii=0;ii<numNodes;ii++){
			for(jj=0;jj<wtsColDim;jj++){
				valArray[ii][(numStates+varNum)*wtsColDim+jj]=
					correctDrvVals[ii].get(jj, dVarNum);
			}
		}
		return(new Matrix(valArray));

	}



private	int [] getOrders() {
		return(getTheState().getTheGrid().getOrders());
	}

	public double [] [] getRanges() {
		return(getTheState().getTheGrid().getRanges());
	}

	public int numNodesNow() {
		return(getTheState().getTheGrid().powersPlusOneProd());
	}

	public int numVarsNow() {
		return((getTheState().getTheGrid().getStateDimWithoutShocks()+
				getTheNonState().getNonStateVarDim())*numNodesNow());
	}


	public NonStateVariablePolynomials getTheNonState() {
		return theNonState;
	}

	public void setTheNonState(NonStateVariablePolynomials theNonState) {
		this.theNonState = theNonState;
	}

	public StateVariablePolynomials getTheState() {
		return theState;
	}

	public void setTheState(StateVariablePolynomials theState) {
		this.theState = theState;
	}
	public int getNumberOfShocks() {
		return getTheState().getTheGrid().getNumberOfShocks();
	}
	public List<NewtonIterInfo> getNewtonIters() {
		return newtonIters;
	}
	public void setNewtonIters(List<NewtonIterInfo> newtonIters) {
		this.newtonIters = newtonIters;
	}
	public double getShrinkFactor() {
		return shrinkFactor;
	}
	public void setShrinkFactor(double shrinkFactor) {
		this.shrinkFactor = shrinkFactor;
	}
	public int getMaxShrink() {
		return maxShrink;
	}
	public void setMaxShrink(int maxShrink) {
		this.maxShrink = maxShrink;
	}
	public int getNewtonSteps() {
		return newtonSteps;
	}
	public void setNewtonSteps(int newtonSteps) {
		this.newtonSteps = newtonSteps;
	}
	protected int getStateVarDim() {
		int stateVarDim=getTheState().getTheGrid().getStateDimWithoutShocks();
		return stateVarDim;
	}
	protected int getNonStateVarDim() {
		int nonStateVarDim;
		//TODO simplify logic so that don't need or
		if((theNonState==null)||(getTheNonState().getRelevantWeightsNSP()==null)) {nonStateVarDim=0;} else 
		{nonStateVarDim=getTheNonState().getRelevantWeightsNSP().getRowDimension();};
		return nonStateVarDim;
	}
	protected int getNodeDim(VarTimeType varTimeType) {
		int nodeDim=getTheState().getTheGrid().powersPlusOneProd();
		return nodeDim;
	}

	protected int getDVarNum(VarTime theDrvVar) {
		int dVarNum=getVarIndex(theDrvVar);
		return dVarNum;
	}
	protected int getVarNumNSP(VarTimeType varTimeType, String varName) {
		int varNum=getTheNonState().varPositionNSP(varName);
		return varNum;
	}
	protected int getDVarNumNSP(VarTime theDrvVar) {
		int dVarNum=getVarIndex(theDrvVar);
		return dVarNum;
	}
	double [] adjustParams(double frac, double[] paramsAway, double []paramsTarget) {
		int ii;int numParams=paramsTarget.length;double [] paramsNow = new double [numParams];
		for(ii=0;ii<numParams;ii++){
			paramsNow[ii]=paramsTarget[ii]*frac+paramsAway[ii]*(1-frac);
		}
		return(paramsNow);
	}
	protected double [][] improveGuess(double [][]guessNow, double[][] guessPrevious) {
		int ii,jj,wtRows=guessNow.length,wtCols=guessNow[0].length;
		for(ii=0;ii<wtRows;ii++){
			for(jj=0;jj<wtCols;jj++){
				guessNow[ii][jj]=guessNow[ii][jj]+guessNow[ii][jj]-guessPrevious[ii][jj];
			}
		}
		return(guessNow);
	}
	public double [] updateParams(double [] initP, double [] termP, int iiNow,
			int numVals) {
		int numP=initP.length;
		double [] nowP = new double [numP];
		int ii;double prop=(1.*iiNow)/(numVals-1);
		for(ii=0;ii<numP;ii++){
			nowP[ii]= (1-prop)*initP[ii]+prop*termP[ii];
		}
		return(nowP);
	}


	public void multiArrayCopy(double[][] source, double[][] destination) {
		for (int aa=0;aa<source.length;aa++)
		{
			System.arraycopy(source[aa],0,destination[aa],0,source[aa].length);
		}
	}



}
