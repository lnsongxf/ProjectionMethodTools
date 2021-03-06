package gov.frb.ma.msu.ProjectionMethodToolsJava;

public class WeightedAtGridPointsBasis extends WeightedStochasticBasis {


//	public WeightedAtGridPointsBasis(NonStateVariablePolynomials aNState) {
//		super(aNState);
		// TODO Auto-generated constructor stub
//	}

	public WeightedAtGridPointsBasis(StateVariablePolynomials aState,NonStateVariablePolynomials aNState) {
		super(aState,aNState);
		// TODO Auto-generated constructor stub
	}

	public void setAllWeights(double [][]xxOriginal){
		double [][] xx =new double[xxOriginal.length][xxOriginal[0].length];
		multiArrayCopy(xxOriginal,xx);

	int stateVarDim=0;
	int nonStateVarDim;
	if(getTheNonState()==null) {nonStateVarDim=0;} else 
	    {nonStateVarDim=getTheNonState().getNonStateVarDim();};
	int nodeDim=xx[0].length;
	int ii;int jj;
	double [][] stateXX = new double[stateVarDim-getNumberOfShocks()][nodeDim];
	double [][] nonStateXX = new double[nonStateVarDim][nodeDim];

	for(ii=0;ii<stateVarDim-getNumberOfShocks();ii++){
	for(jj=0;jj<nodeDim;jj++){
	    stateXX[ii][jj]=xx[ii][jj];
	}}
	for(ii=0;ii<nonStateVarDim;ii++){
	for(jj=0;jj<nodeDim;jj++){
	    nonStateXX[ii][jj]=xx[stateVarDim-getNumberOfShocks()+ii][jj];
	}}
//	getTheState().setRelevantEvaluatedChebPolys(getTheState().getTheGrid().getTheChebPoly());
	/*if(stateXX.length>0)*/{this.setTheStateWeights(stateXX);}
	if((getTheNonState() != null)&&(nonStateXX.length>0)){this.setTheNonStateWeights(nonStateXX);}

	}
	public void setAllWeights(int nodeDim){

		int stateVarDim=0;
		int nonStateVarDim;
		if(getTheNonState()==null) {nonStateVarDim=0;} else 
		    {nonStateVarDim=getTheNonState().getNonStateVarDim();};
	double [][] xx = new double[stateVarDim+nonStateVarDim][nodeDim];
	setAllWeights(xx);
	}
	protected int getStateVarDim() {
		return 0;
	}
}
