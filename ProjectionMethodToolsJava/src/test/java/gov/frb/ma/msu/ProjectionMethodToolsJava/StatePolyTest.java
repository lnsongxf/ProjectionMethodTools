package gov.frb.ma.msu.ProjectionMethodToolsJava;

import gov.frb.ma.msu.ProjectionMethodToolsJava.GridPointsSpec;
import gov.frb.ma.msu.ProjectionMethodToolsJava.GridVarSpec;
import gov.frb.ma.msu.ProjectionMethodToolsJava.GridVars;
import gov.frb.ma.msu.ProjectionMethodToolsJava.StateVariablePolynomials;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class StatePolyTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StatePolyTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( StatePolyTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws Exception
    {
	assertTrue(true);
    }
    public void testConstructors() throws Exception
    {

	/*

	(*define variable grid*)
kk=JavaNew["gov.frb.ma.msu.varSpec","kk",0,25];
theta=JavaNew["gov.frb.ma.msu.varSpec","theta",-10,10];
vsArray={kk,theta};
theVars=JavaNew["gov.frb.ma.msu.varSpecs",vsArray];
theOrds={1,2};
theGS=JavaNew["gov.frb.ma.msu.gridSpec",theVars,theOrds];
someWts={{1,2,3,4,5,6},{8,2,3,4,5,6}};
sPoly=JavaNew["gov.frb.ma.msu.StateVariablePolynomials",theGS,someWts];

jamaRes=sPoly@nonStateVarsAtChenbNodePtsTimeT;
atNodes=jamaRes[getArray[]]

polys=Flatten[Outer[Times,phiFunc[xxb,2],phiFunc[xxa,1]]];
theNodes=Flatten[Outer[{#2,#1}&,chebyshevNodes[3],chebyshevNodes[2]],1]//N
atMthNodes=Transpose[(polys/.{xxa->#[[1]],xxb->#[[2]]}) & /@ theNodes];
jamaRes=sPoly@nonStateVarsAtChenbNodePtsTimeT;
atNodes=jamaRes[getArray[]]
Reap[Max[Abs[Flatten[
Sow[theRes=(someWts .atMthNodes)] -   atNodes]]]]

	 */

	/*define variable grid*/
	GridVarSpec kk=new GridVarSpec("kk",-7,25);
	GridVarSpec theta=new GridVarSpec("theta",-10,20);
	GridVarSpec[] vsArray={kk,theta};
	GridVars theVars=new GridVars(vsArray);
	int [] theOrds={1,2};
	GridPointsSpec theGS=new GridPointsSpec(theVars,theOrds);
	double[][] someWts={{1,2,3,4,5,6},{8,2,3,4,5,6}};
	StateVariablePolynomials sPoly=new StateVariablePolynomials(theGS);
	sPoly.setTheWeights(someWts);
	double[][] forComp=sPoly.getVariablesAtChebyshevNodesTimeT().transpose().getArray();
	double [][] rghtRes=
	    {{12.083099860069229, 0.11305256263739993, -6.828427124746191, 
	      -1.1715728752538093, 1.9879679517962416, -0.18412037450287544}, 
	     {19.083099860069233, 7.1130525626373995, 0.17157287525381015, 
	      5.828427124746191, 8.987967951796243, 6.815879625497125}};
	int ii;int jj;
	for(ii=0;ii<2;ii++){
	    for(jj=0;jj<6;jj++){
		assertEquals(rghtRes[ii][jj],forComp[ii][jj],1e-7);
	    }}


	/*

(*check level and derivs*)
xFormed=polys/.{xxa->toChebyshevInterval[xxa,0,25],
xxb->toChebyshevInterval[xxb,-10,10]}
preForEvalMth=Function @@ {{xxa,xxb},(Transpose[{xFormed}])}
forEvalMth=Function @@ {{xxa,xxb},(someWts.Transpose[{xFormed}])}

forWtsEvalMth=Function @@ 
{{xxa,xxb},({{w1,w2,w3,w4,w5,w6}}.Transpose[{xFormed}])}
notforWtsEvalMth=Function @@ {{xxa,xxb},({{w1,w2,w3,w4,w5,w6}}.Transpose[{polys}])}


aState={3.7,4.4};



theWtFuncs=Flatten[forWtsEvalMth @@ aState];
ArrayFlatten[{{D[theWtFuncs,#]& /@ {w1,w2,w3,w4,w5,w6},
D[theWtFuncs,#]& /@ {w1,w2,w3,w4,w5,w6}}}]




forEval=sPoly@evalWithWts;
forEval[evaluate[aState]]
Reap[Max[Abs[Flatten[
Sow[Flatten[forEvalMth @@ aState//N]] -   forEval[evaluate[aState]]]]]]


forEvalDrv=sPoly@evalWDrvWithWts;
forEvalDrv[evaluate[aState]]
Reap[Max[Abs[Flatten[
Sow[ArrayFlatten[{{D[theWtFuncs,#]& /@ {w1,w2,w3,w4,w5,w6},
D[theWtFuncs,#]& /@ {w1,w2,w3,w4,w5,w6}}}]] -   forEvalDrv[evaluate[aState]]]]]]

	*/

	double [] mltRes={-2.7323599999999995,4.26764};
	double [] aState={3.7,4.4};
/*	double[] theRes=sPoly.getEvalWithWts().evaluate(aState);

	assertEquals(mltRes[0],theRes[0],1e-6);
	assertEquals(mltRes[1],theRes[1],1e-6);*/
double [][] theRghtDrvRes=
{
 {
  1.0,
  1.0,
 },
 {
  -0.33125000000000004,
  -0.33125000000000004,
 },
 {
  -0.039999999999999925,
  -0.039999999999999925,
 },
 {
  0.013249999999999977,
  0.013249999999999977,
 },
 {
  -0.9968,
  -0.9968,
 },
 {
  0.33019000000000004,
  0.33019000000000004,
 }
    };
	


//	double[][] theDrvRes=sPoly.getEvalWDrvWithWts().evaluate(aState);

/*
	for(ii=0;ii<6;ii++){
	    for(jj=0;jj<2;jj++){
		assertEquals(theRghtDrvRes[ii][jj],theDrvRes[ii][jj],1e-7);
	    }}


*/

	


	double [][] chgWts={{-.1,0,0,0,0,-.4},{0,0,0,-.3,0,-1}};
	sPoly.setTheWeights(chgWts);
	double [] chgRghtRes={-0.23207600000000003,-0.33416500000000005};
	double [][] chgRghtDrvRes=
	    {
	       {
		1.0,
		1.0,
	       },
	       {
		-0.33125000000000004,
		-0.33125000000000004,
	       },
	       {
		-0.039999999999999925,
		-0.039999999999999925,
	       },
	       {
		0.013249999999999977,
		0.013249999999999977,
	       },
	       {
		-0.9968,
		-0.9968,
	       },
	       {
		0.33019000000000004,
		0.33019000000000004,
}
};
/*	mltRes=sPoly.getEvalWithWts().evaluate(aState);
//double [][]	mltDrvRes=sPoly.getEvalWDrvWithWts().evaluate(aState);

	assertEquals(chgRghtRes[0],mltRes[0],1e-6);
	assertEquals(chgRghtRes[1],mltRes[1],1e-6);*/

/*
	for(ii=0;ii<6;ii++){
	    for(jj=0;jj<2;jj++){
		assertEquals(chgRghtDrvRes[ii][jj],mltDrvRes[ii][jj],1e-7);
	    }}

*/





    }
    public void testForwardIteration() throws Exception
    {
	/*define variable grid*/
	GridVarSpec kk=new GridVarSpec("kk",-7,25);
	GridVarSpec theta=new GridVarSpec("theta",-10,25);
	GridVarSpec[] vsArray={kk,theta};
	GridVars theVars=new GridVars(vsArray);
	int [] theOrds={1,2};
	double[] noShock={};
	GridPointsSpec theGS=new GridPointsSpec(theVars,theOrds);
	double[][] someWts={{1,2,3,4,5,6},{8,2,3,4,5,6}};
	StateVariablePolynomials sPoly=new StateVariablePolynomials(theGS);
	sPoly.setTheWeights(someWts);
	double [][] iterWts={{5, 2, 3, 4, 5, 6}, {8, 2, 3, 4, 5, 6}};
	sPoly.setTheWeights(iterWts);
	double [] aState={3.7,4.4};
//	double [] itRghtVal={1.8943713693249644,4.894371369324965};
	double [] itRghtVal={1.217347755102042, 4.2173477551020415};
	double [] itVal= 
//	    sPoly.evaluateShock(aState,noShock);
    sPoly.evaluate(aState);
		
	assertEquals(itRghtVal[0],itVal[0],1e-7);
	assertEquals(itRghtVal[1],itVal[1],1e-7);


    }
    public void testNotConstructors() throws Exception
    {

}

    public void testIterateDrvs() throws Exception
    {
	/*define variable grid*/
	GridVarSpec kk=new GridVarSpec("kk",-4,25);
	GridVarSpec theta=new GridVarSpec("theta",-10,20);
	GridVarSpec[] vsArray={kk,theta};
	GridVars theVars=new GridVars(vsArray);
	int [] theOrds={1,2};

	GridPointsSpec theGS=new GridPointsSpec(theVars,theOrds);
	double[][] someWts={{5,1,3,-3,5,6},{8,2,-3,4,7,6}};
	StateVariablePolynomials sPoly=new StateVariablePolynomials(theGS);
	sPoly.setTheWeights(someWts);
	double [] aState={3.7,4.4};
//	double [] itRghtVal= {2.318137387142901,4.088807307862802};
	double [] itRghtVal= {2.175547586206896, 3.084292413793103};
	double[] noShock={};
	double [] itVal= 
//	    sPoly.evaluateShock(aState,noShock);
    sPoly.evaluate(aState);
	assertEquals(itRghtVal[0],itVal[0],1e-7);
	assertEquals(itRghtVal[1],itVal[1],1e-7);
double [][] rghtDrv=
    {
     {
      0.6950945802450631,
      -0.29759485931102103,
     },
     {
      -0.4311100385834161,
      0.13956172712516846,
     },
     {
      -0.1155176222902624,
      0.011903794372440818,
     },
     {
      0.06760093116101899,
      -0.005582469085006729,
     },
     {
      -0.6634486282029397,
      0.2966425557612258,
     },
     {
      0.4128395126500484,
      -0.13911512959836791,
     },
     {
      0.2618477725620266,
      0.5258203140338193,
     },
     {
      -0.12279757609805385,
      -0.35172624477400527,
     },
     {
      -0.010473910902481046,
      -0.10874665164181266,
     },
     {
      0.004911903043922145,
      0.06442557940864256,
     },
     {
      -0.26100985968982815,
      -0.4947160396435719,
     },
     {
      0.12240462385454008,
      0.33370974698082767,
     }
    };

/*	double [][] itDrv= 
	    sPoly.getIterateDeterministicWDrvWithWts().evaluate(aState);

	int ii;int jj;

	for(ii=0;ii<12;ii++){
	for(jj=0;jj<2;jj++){
	    assertEquals(rghtDrv[ii][jj],itDrv[ii][jj],1e-8);
	}}*/
    }

    public void testIterate5Drvs() throws Exception
    {
	/*define variable grid*/
	GridVarSpec aa=new GridVarSpec("aa",-4,25);
	GridVarSpec bb=new GridVarSpec("bb",-10,20);
	GridVarSpec cc=new GridVarSpec("cc",-3,20);
	GridVarSpec dd=new GridVarSpec("dd",-10,20);
	GridVarSpec ee=new GridVarSpec("ee",-7,100);
	GridVarSpec[] vsArray={aa,bb,cc,dd,ee};
	GridVars theVars=new GridVars(vsArray);
	int [] theOrds={1,1,1,1,1};
	GridPointsSpec theGS=new GridPointsSpec(theVars,theOrds);
	double[][] someWts=
{{2.707763036188241, 2.847266651434029, 0.024365102272993603, 
1.8465816495412994, 0.9805132002576666, -0.061971725768941534, 
1.850487272219754, 0.41761644371695233, 0.9211840216591111, 
2.591526125286956, 2.533164922921149, 0.8986935460489699, 1.93396401902399, 
2.459245934657113, 0.17569782510624332, 1.5252843135641534, 
-0.061862434775058296, 0.7797741415759227, -0.17461804039599216, 
0.0770365632421851, -0.04151375040878197, 2.061274807511982, 
2.9171709399896892, 1.8220024885556452, 0.0017681201957785724, 
1.300764407837547, 2.9329308609352256, -0.11064039519715675, 
2.4241441053640815, 1.5657693352116906, 2.019973490878957, 
-0.2141028204481156}, {-0.23314598603240327, 2.6430090765398155, 
2.3044996715906603, 2.262855518085211, 1.4783454098704751, 
1.3828151418228911, 0.4954909622632083, 2.7387809243699266, 
0.41552431138089324, 2.010750648352576, -0.036103195571173176, 
0.7098546517400806, 1.333308407792272, 2.9988280541580234, 
1.7131242926501409, 2.618253917996748, 0.04607435328253476, 
2.474282892293341, 0.6542256052130988, 1.4451777341698553, 
-0.008560730300431467, 1.1564269456282235, 0.21048918714522682, 
1.9919509709483618, 2.914837337232789, 0.7654741553059903, 
0.42469804368148434, 1.5106719642993491, 2.1133675228194635, 
0.6913726290200582, 1.6457671950640629, 2.396532835464382}, 
{2.462073914948115, 1.443854427266245, 0.4235509943170262, 
0.09279028459102756, 1.9936235593834077, 1.1325989544677533, 
0.6498140573435103, 2.1624315529002667, 0.9323503771620023, 
1.153240737231061, 2.8902739420960666, 2.4611138859574337, 
1.0786681478370062, 2.16187948565172, 1.6538966071831027, 
-0.10913394887235556, 1.3410988008938114, 0.7825137682524759, 
-0.04669992076566071, 2.630606834637243, 0.5458005113318676, 
1.1246801400803754, 0.17488454256246136, 0.5572764353483546, 
1.4079076981401923, 1.5153767521671948, -0.035222743239573834, 
2.259936426019466, 1.1333946182250656, 0.6738810407361329, 2.570670476944792, 
1.0974974354028804}, {2.1242215526807073, 0.348718339132916, 
0.011118513014997227, 0.0656837609958836, 1.153301287597116, 
2.571624911122123, 0.3173168680606043, 0.8979394135783998, 
0.43549461442896004, 1.3498031485859239, -0.27405759030906707, 
1.1331712002054817, 2.1040850277943663, 2.3858797849473743, 
-0.20715144906202768, 1.7026551007012747, 2.943983690305351, 
0.6064293380436673, -0.11345658347732933, 1.0946754422240554, 
-0.06310989030560256, 2.426656191245914, 2.5046124036654263, 
-0.14374936438957894, 1.31388991104042, 0.6514848874336205, 
0.02974353586608263, 1.1603578578273166, 0.1508811460913793, 
1.7275047087553412, -0.21991643057220664, 1.4034391917221825}, 
{2.550488109019882, 2.6931546748991, 2.7832227386047204, 0.5297436091737527, 
1.6475261133299883, 2.4531691764902033, -0.2509533504420761, 
0.09019764722223396, 2.8692727510750795, 0.5502748303907903, 
1.256615525398124, 1.4584740408845567, 1.2446255065126544, 2.628519500017679, 
1.148283553105758, 0.025669103389319853, -0.23948365077404507, 
-0.07653746456869001, 1.5611301629986147, -0.009977155126179199, 
1.0225991594937738, 0.4996154846843865, 1.6946925097657641, 
1.5627106736045056, 0.99142371870499, 1.710038350704483, 
-0.04748998037187805, 2.030578430741268, 1.5317546378947318, 
 0.07716498321782513, 0.3553229383639505, 0.5402603898790632}};
	StateVariablePolynomials sPoly=new StateVariablePolynomials(theGS);
	sPoly.setTheWeights(someWts);
	double [] aState={3.7, 4.4, 2.2, -1.4, 2.4};
	double [] itRghtVal= 
{0.5262051565139518, -0.5528951945713327, 0.1060376728665857, -0.02141010297935348, 0.14656982306624836};
	double[] noShock={};
/*not testing for result in range*/
//	double [] itVal= 	    sPoly.evaluateShock(aState,noShock);
double []itVal=sPoly.evaluate(aState);
	assertEquals(itRghtVal[0],itVal[0],1e-7);
	assertEquals(itRghtVal[1],itVal[1],1e-7);
	assertEquals(itRghtVal[2],itVal[2],1e-7);
	assertEquals(itRghtVal[3],itVal[3],1e-7);
	assertEquals(itRghtVal[4],itVal[4],1e-7);
double [][] rghtDrv=
{
 {
  1.1583119586872614,
  0.009159618127281673,
  0.17316824884925047,
  6.983008768776293E-4,
  0.11617170574892746,
 },
 {
  -0.7620907698316846,
  -0.004295545052794164,
  -0.08120993739137262,
  -3.274790319150261E-4,
  -0.054480524075359076,
 },
 {
  -0.3765254913189126,
  -3.663847250912662E-4,
  -0.006926729953970006,
  -2.793203507510512E-5,
  -0.00464686822995709,
 },
 {
  0.25760620804357715,
  1.718218021117662E-4,
  0.003248397495654899,
  1.3099161276601021E-5,
  0.002179220963014359,
 },
 {
  -0.8166371884228837,
  -0.005017877756684742,
  -0.09486608415219808,
  -3.825474368981795E-4,
  -0.06364189097549938,
 },
 {
  0.5427390853669666,
  0.002353211637617672,
  0.044488922223099775,
  1.7940155661431864E-4,
  0.029845852319544532,
 },
 {
  0.2736765928903756,
  2.0071511026738931E-4,
  0.003794643366087916,
  1.530189747592715E-5,
  0.002545675639019971,
 },
 {
  -0.18748855098690032,
  -9.41284655047067E-5,
  -0.001779556888923988,
  -7.1760622645727325E-6,
  -0.0011938340927817792,
 },
 {
  -0.40230710923852187,
  -0.003908103734306847,
  -0.07388511950901352,
  -2.979417074677885E-4,
  -0.049566594452875715,
 },
 {
  0.2619413822261786,
  0.0018327658891921761,
  0.03464957328698565,
  1.3972438695041115E-4,
  0.02324502360548654,
 },
 {
  0.12662791978740034,
  1.5632414937227356E-4,
  0.0029554047803605353,
  1.1917668298711517E-5,
  0.0019826637781150246,
 },
 {
  -0.08650936222367475,
  -7.331063556768691E-5,
  -0.0013859829314794236,
  -5.588975478016435E-6,
  -9.298009442194599E-4,
 },
 {
  0.28134878497255766,
  0.0021409611761854896,
  0.04047619590493784,
  1.6322023974322324E-4,
  0.027153873482879735,
 },
 {
  -0.18542571792577867,
  -0.0010040369653835399,
  -0.01898194014852257,
  -7.65446641554426E-5,
  -0.012734230323005667,
 },
 {
  -0.09193499135570808,
  -8.563844704741944E-5,
  -0.0016190478361975107,
  -6.5288095897289185E-6,
  -0.0010861549393151875,
 },
 {
  0.06291331425415399,
  4.016147861534152E-5,
  7.592776059409016E-4,
  3.061786566217699E-6,
  5.093692129202258E-4,
 },
 {
  -0.9969156552344296,
  -0.0075502646619275094,
  -0.14274242568695225,
  -5.756087601925879E-4,
  -0.09576022847715329,
 },
 {
  0.6571629945829565,
  0.003540813772490142,
  0.06694127549457071,
  2.69940659952386E-4,
  0.044908245078940846,
 },
 {
  0.3259622107268014,
  3.0201058647709976E-4,
  0.005709697027478079,
  2.3024350407703472E-5,
  0.003830409139086124,
 },
 {
  -0.22306989410439257,
  -1.416325508996054E-4,
  -0.002677651019782823,
  -1.079762639809542E-5,
  -0.0017963298031576306,
 },
 {
  0.7038972094606631,
  0.0041362319452298525,
  0.07819802450676515,
  3.153334947142003E-4,
  0.052459951252701364,
 },
 {
  -0.4685264748245938,
  -0.001939750153625034,
  -0.03667217701006917,
  -1.4788053545217668E-4,
  -0.024601908173680635,
 },
 {
  -0.236972550880618,
  -1.654492778091938E-4,
  -0.0031279209802705997,
  -1.2613339788567987E-5,
  -0.0020983980501080505,
 },
 {
  0.16237516600666121,
  7.759000614500122E-5,
  0.001466887080402764,
  5.9152214180870565E-6,
  9.840763269472236E-4,
 },
 {
  0.345721557405727,
  0.003221446255755737,
  0.060903434959766294,
  2.455930710155042E-4,
  0.0408576974835854,
 },
 {
  -0.2256168182864062,
  -0.0015107472095957936,
  -0.028561610877683496,
  -1.1517468157968469E-4,
  -0.019160851233681424,
 },
 {
  -0.10959906525706561,
  -1.2885785023022923E-4,
  -0.002436137398390647,
  -9.823722840620148E-6,
  -0.0016343078993434129,
 },
 {
  0.07490000765907028,
  6.042988838383165E-5,
  0.0011424644351073381,
  4.60698726318738E-6,
  7.664340493472557E-4,
 },
 {
  -0.24220740198769497,
  -0.0017647922966314035,
  -0.03336449045621979,
  -1.3454229107805876E-4,
  -0.02238291253448591,
 },
 {
  0.15992548150392918,
  8.276267322133477E-4,
  0.015646795524296174,
  6.309569512626203E-5,
  0.010496814154103735,
 },
 {
  0.07959190266343676,
  7.059169186525602E-5,
  0.0013345796182487894,
  5.381691643122342E-6,
  8.95316501379435E-4,
 },
 {
  -0.05448006966621701,
  -3.3105069288533857E-5,
  -6.258718209718459E-4,
  -2.523827805050477E-6,
  -4.198725661641487E-4,
 },
 {
  -0.10519381541813659,
  1.0865921775004528,
  0.10824183515654595,
  0.17508344156665206,
  0.0971141476742435,
 },
 {
  0.049332272058160605,
  -0.7284566655509742,
  -0.05076168821134568,
  -0.08210809673470579,
  -0.045543186495507294,
 },
 {
  0.004207752616725456,
  -0.37365670007144025,
  -0.00432967340626183,
  -0.00700333766266607,
  -0.003884565906969733,
 },
 {
  -0.00197329088232642,
  0.2562608438723487,
  0.0020304675284538234,
  0.0032843238693882255,
  0.0018217274598202884,
 },
 {
  0.057627916272544384,
  -0.7773472213379363,
  -0.05929770099880343,
  -0.09591527668433981,
  -0.053201663508498614,
 },
 {
  -0.027025505562296673,
  0.5243134456305775,
  0.027808577020128497,
  0.04498095734162142,
  0.02494974564536486,
 },
 {
  -0.002305116650901771,
  0.2721049942069777,
  0.0023719080399521326,
  0.0038366110673735856,
  0.0021280665403399404,
 },
 {
  0.001081020222491865,
  -0.18675152539744475,
  -0.001112343080805138,
  -0.0017992382936648538,
  -9.979898258145926E-4,
 },
 {
  0.04488269457840494,
  -0.37170666926548346,
  -0.04618318300012627,
  -0.07470226840177155,
  -0.04143536967434389,
 },
 {
  -0.021048436078148523,
  0.2475908310664089,
  0.021658320303507488,
  0.035032787940141134,
  0.019431759571416442,
 },
 {
  -0.0017953077831361942,
  0.1254039021884788,
  0.0018473273200050472,
  0.002988090736070856,
  0.0016574147869737525,
 },
 {
  8.419374431259393E-4,
  -0.08593534017728396,
  -8.66332812140298E-4,
  -0.0014013115176056427,
  -7.772703828566564E-4,
 },
 {
  -0.02458791094295227,
  0.2645850656829801,
  0.025300352426156126,
  0.040923851385318316,
  0.02269937643029274,
 },
 {
  0.011530882373246579,
  -0.17756411163825264,
  -0.011864992861921492,
  -0.019191875132425137,
  -0.010645224808689006,
 },
 {
  9.83516437718089E-4,
  -0.09126444258412499,
  -0.0010120140970462433,
  -0.0016369540554127299,
  -9.079750572117079E-4,
 },
 {
  -4.6123529492986243E-4,
  0.06259885000265294,
  4.745997144768589E-4,
  7.676750052970043E-4,
  4.2580899234755955E-4,
 },
 {
  0.08671116373719297,
  -0.9377971066299761,
  -0.08922364355894723,
  -0.14432111725400665,
  -0.08005110116699325,
 },
 {
  -0.04066454575261463,
  0.6294384338581093,
  0.04184281215178214,
  0.06768162740187897,
  0.037541206064520966,
 },
 {
  -0.003468446549487712,
  0.32359746878262324,
  0.0035689457423578818,
  0.0057728446901602545,
  0.0032020440466797233,
 },
 {
  0.001626581830104582,
  -0.2219609116753987,
  -0.0016737124860712826,
  -0.002707265096075154,
  -0.0015016482425808358,
 },
 {
  -0.04750263752559267,
  0.6715105263121364,
  0.04887903951490152,
  0.07906287293045582,
  0.04385408150887456,
 },
 {
  0.022277098977519314,
  -0.4533382372101123,
  -0.022922584048367604,
  -0.037077761098420654,
  -0.020566052017954962,
 },
 {
  0.0019001055010237028,
  -0.23567708355467692,
  -0.001955161580596057,
  -0.0031625149172182262,
  -0.0017541632603549789,
 },
 {
  -8.91083959100771E-4,
  0.16176763650208198,
  9.169033619347025E-4,
  0.0014831104439368234,
  8.226420807181969E-4,
 },
 {
  -0.03699676319453567,
  0.3204976433344935,
  0.03806875458515081,
  0.06157701002837617,
  0.03415513649791712,
 },
 {
  0.017350206187782238,
  -0.21378767237713808,
  -0.01785293318476038,
  -0.028877494358135025,
  -0.01601758125419561,
 },
 {
  0.0014798705277814237,
  -0.10859010869421627,
  -0.0015227501834060295,
  -0.002463080401135042,
  -0.0013662054599166819,
 },
 {
  -6.940082475112883E-4,
  0.07442684182269955,
  7.141173273904139E-4,
  0.001155099774325399,
  6.407032501678234E-4,
 },
 {
  0.020267792010919532,
  -0.22838908384432358,
  -0.02085505685969131,
  -0.03373349245032781,
  -0.018711074777119807,
 },
 {
  -0.009504895563741572,
  0.15344516678841708,
  0.00978030252730351,
  0.015819844735326143,
  0.008774848860994115,
 },
 {
  -8.107116804367799E-4,
  0.0790391699377019,
  8.342022743876511E-4,
  0.0013493396980131102,
  7.48442991084791E-4,
 },
 {
  3.801958225496623E-4,
  -0.05422085707759653,
  -3.9121210109213975E-4,
  -6.327937894130446E-4,
  -3.50993954439764E-4,
 },
 {
  0.192277141820833,
  0.18038423640230178,
  1.174388594988586,
  0.11583593288103196,
  0.03877209751335166,
 },
 {
  -0.09017134926770098,
  -0.08459398672659668,
  -0.7696301578902367,
  -0.05432305817869084,
  -0.018182776764882155,
 },
 {
  -0.007691085672833305,
  -0.0072153694560920575,
  -0.37716855677096556,
  -0.0046334373152412695,
  -0.0015508839005340634,
 },
 {
  0.0036068539707080323,
  0.003383759469063861,
  0.2579077835659192,
  0.0021729223271476296,
  7.273110705952848E-4,
 },
 {
  -0.10533443421489111,
  -0.09881919037691314,
  -0.8254443891792613,
  -0.06345794583917402,
  -0.021240366463836122,
 },
 {
  0.04939821742491444,
  0.04634279272848339,
  0.5468693588251299,
  0.029759588393543673,
  0.009960999445109352,
 },
 {
  0.004213377368595637,
  0.003952767615076519,
  0.2740288809206307,
  0.0025383178335669563,
  8.496146585534434E-4,
 },
 {
  -0.0019759286969965742,
  -0.0018537117091393326,
  -0.18765376192522684,
  -0.001190383535741745,
  -3.984399778043734E-4,
 },
 {
  -0.08203824717688873,
  -0.07696394086498208,
  -0.4091664740604202,
  -0.04942333136257363,
  -0.016542761605696708,
 },
 {
  0.03847310902088575,
  0.036093434336681246,
  0.2651581877978275,
  0.023177838156241425,
  0.007757984753016385,
 },
 {
  0.0032815298870755433,
  0.0030785576345992776,
  0.12690229438027628,
  0.0019769332545029415,
  6.61710464227867E-4,
 },
 {
  -0.0015389243608354272,
  -0.0014437373734672474,
  -0.08663803444654071,
  -9.271135262496553E-4,
  -3.103193901206549E-4,
 },
 {
  0.044942691931686866,
  0.04216285456081627,
  0.2851065239619455,
  0.02707539022471425,
  0.009062556357903411,
 },
 {
  -0.021076572767963493,
  -0.019772924897486246,
  -0.18718796793459502,
  -0.0126974243812453,
  -0.004250026429913323,
 },
 {
  -0.0017977076772674717,
  -0.0016865141824326478,
  -0.0920853009152836,
  -0.001083015608988568,
  -3.6250225431613587E-4,
 },
 {
  8.430629107185383E-4,
  7.909169958994485E-4,
  0.06298380425450664,
  5.078969752498111E-4,
  1.7000105719653267E-4,
 },
 {
  -0.15849386830464926,
  -0.14869055748301885,
  -1.0101676115127176,
  -0.09548345121595345,
  -0.03195980374465062,
 },
 {
  0.07432815892907689,
  0.06973074419893296,
  0.6633777051134638,
  0.04477844608748161,
  0.014988045894043046,
 },
 {
  0.006339754732185957,
  0.005947622299320742,
  0.3264922889779329,
  0.0038193380486381302,
  0.0012783921497860223,
 },
 {
  -0.00297312635716307,
  -0.0027892297679573135,
  -0.22331848252561287,
  -0.001791137843499261,
  -5.995218357617208E-4,
 },
 {
  0.08682707567993829,
  0.08145656627330597,
  0.7111569768131165,
  0.052308325448739715,
  0.017508414225330338,
 },
 {
  -0.040718904456798634,
  -0.03820032073506762,
  -0.47193105537608915,
  -0.02453080090009862,
  -0.008210842533258364,
 },
 {
  -0.0034730830271975247,
  -0.0032582626509322326,
  -0.2372629415747161,
  -0.002092333017949584,
  -7.003365690132122E-4,
 },
 {
  0.0016287561782719424,
  0.0015280128294027022,
  0.16251134922872104,
  9.81232036003943E-4,
  3.2843370133033397E-4,
 },
 {
  0.06762405047665035,
  0.06344130452608804,
  0.3513757254177965,
  0.040739605852140134,
  0.013636182931050931,
 },
 {
  -0.031713347809739466,
  -0.029751784191544728,
  -0.22826842811275605,
  -0.01910547033065882,
  -0.006394899581458366,
 },
 {
  -0.0027049620190660087,
  -0.0025376521810435166,
  -0.1098252319775484,
  -0.0016295842340856021,
  -5.454473172420361E-4,
 },
 {
  0.0012685339123895765,
  0.0011900713676617873,
  0.07500607205212427,
  7.642188132263515E-4,
  2.557959832583342E-4,
 },
 {
  -0.03704621895677366,
  -0.034754801609943875,
  -0.24530490272474176,
  -0.02231821885812894,
  -0.007470256736140943,
 },
 {
  0.01737339923490075,
  0.01629880351362885,
  0.16137810253923388,
  0.010466475050708742,
  0.0035032928141902346,
 },
 {
  0.0014818487582709439,
  0.0013901920643977527,
  0.07971580269291863,
  8.927287543251561E-4,
  2.9881026944563724E-4,
 },
 {
  -6.949359693960288E-4,
  -6.519521405451529E-4,
  -0.0545381745076292,
  -4.18659002028349E-4,
  -1.4013171256760916E-4,
 },
 {
  0.006674555844095519,
  -0.0996477459903133,
  -0.02446645607265669,
  0.8541030155247582,
  0.2499695487630906,
 },
 {
  -0.0031301365337827254,
  0.04673135674028485,
  0.01147392422717693,
  -0.6194272654520279,
  -0.11722709873027695,
 },
 {
  -2.6698223376382023E-4,
  0.003985909839612525,
  9.786582429062657E-4,
  -0.3643571335924125,
  -0.009998781950523606,
 },
 {
  1.252054613513088E-4,
  -0.0018692542696113906,
  -4.589569690870763E-4,
  0.25189966786839085,
  0.004689083949211069,
 },
 {
  -0.003656495810243632,
  0.05458963475991076,
  0.013403362891977142,
  -0.6499835934729905,
  -0.13693983975717136,
 },
 {
  0.0017147704489418407,
  -0.025600656301199523,
  -0.006285715011409969,
  0.4645842960111546,
  0.06422006278267345,
 },
 {
  1.4625983240974498E-4,
  -0.002183585390396426,
  -5.361345156790847E-4,
  0.26701044909237986,
  0.005477593590286844,
 },
 {
  -6.859081795767351E-5,
  0.0010240262520479792,
  2.514286004563983E-4,
  -0.18436235941266785,
  -0.0025688025113069337,
 },
 {
  -0.0028478104934807546,
  0.042516371622533675,
  0.010439021257666855,
  -0.27251129348918707,
  -0.10665367413891864,
 },
 {
  0.0013355249210806296,
  -0.019938712209188202,
  -0.004895541003595489,
  0.20107162035752507,
  0.0500168954582515,
 },
 {
  1.1391241973922996E-4,
  -0.0017006548649013437,
  -4.1756085030667336E-4,
  0.12143608715742696,
  0.004266146965556738,
 },
 {
  -5.3420996843225085E-5,
  7.975484883675267E-4,
  1.9582164014381924E-4,
  -0.08407457174892861,
  -0.0020006758183300564,
 },
 {
  0.0015601048790372827,
  -0.02329157749756192,
  -0.00571876816724358,
  0.21024325112726996,
  0.058427664963059775,
 },
 {
  -7.316353915485187E-4,
  0.010922946688511796,
  0.00268190507153492,
  -0.15207967446729892,
  -0.027400560120607338,
 },
 {
  -6.24041951614912E-5,
  9.316630999024752E-4,
  2.287507266897428E-4,
  -0.08909077000189658,
  -0.002337106598522387,
 },
 {
  2.9265415661940697E-5,
  -4.369178675404711E-4,
  -1.0727620286139662E-4,
  0.0615794725158148,
  0.0010960224048242918,
 },
 {
  -0.005501830144385278,
  0.08213954389108068,
  0.020167676874844113,
  -0.7461565076930017,
  -0.20604966542901487,
 },
 {
  0.002580168619435854,
  -0.038520613686851626,
  -0.009457945017168273,
  0.5395656012531833,
  0.0966301879253311,
 },
 {
  2.2007320577541067E-4,
  -0.003285581755643221,
  -8.06707074993763E-4,
  0.3159318448251443,
  0.00824198661716058,
 },
 {
  -1.0320674477743398E-4,
  0.001540824547474062,
  3.783178006867302E-4,
  -0.21836599837120166,
  -0.0038652075170132368,
 },
 {
  0.003014046079098022,
  -0.0449981849142442,
  -0.011048379505349383,
  0.5665248068944895,
  0.1128793819306777,
 },
 {
  -0.0014134836784735548,
  0.021102597063231757,
  0.005181309009405227,
  -0.40410355500045725,
  -0.052936537733007466,
 },
 {
  -1.2056184316392063E-4,
  0.0017999273965697644,
  4.4193518021397446E-4,
  -0.23147765477797105,
  -0.0045151752772270995,
 },
 {
  5.653934713894209E-5,
  -8.441038825292688E-4,
  -2.0725236037620872E-4,
  0.15979824921369576,
  0.002117461509320295,
 },
 {
  0.002347447528271052,
  -0.035046205393527756,
  -0.008604875466600154,
  0.23873098778805105,
  0.08791452391637967,
 },
 {
  -0.001100871944292631,
  0.01643546183972336,
  0.004035389873991796,
  -0.175441930465703,
  -0.041228880181474595,
 },
 {
  -9.389790113084189E-5,
  0.0014018482157411075,
  3.441950186640055E-4,
  -0.10531944247235858,
  -0.00351658095665518,
 },
 {
  4.403487777170517E-5,
  -6.574184735889333E-4,
  -1.6141559495967158E-4,
  0.07289301214624215,
  0.0016491552072589813,
 },
 {
  -0.001285992993748489,
  0.019199225563410853,
  0.004713975255615736,
  -0.18359517689279425,
  -0.048161869623755814,
 },
 {
  6.0308636948205E-4,
  -0.009003774746978882,
  -0.0022106918440128963,
  0.13243836904563092,
  0.022586256099416516,
 },
 {
  5.143971974993948E-5,
  -7.679690225364329E-4,
  -1.8855901022462912E-4,
  0.07724741365964073,
  0.0019264747849502292,
 },
 {
  -2.4123454779281958E-5,
  3.6015098987915466E-4,
  8.842767376051571E-5,
  -0.05338058516788508,
  -9.034502439766591E-4,
 },
 {
  0.033752967797539654,
  -0.03236953941338828,
  0.023925154002084736,
  0.08007799804078954,
  0.9841174693839728,
 },
 {
  -0.0158289780016048,
  0.015180197793864845,
  -0.011220072221667323,
  -0.037553819770853016,
  -0.6803995610549698,
 },
 {
  -0.0013501187119015837,
  0.0012947815765355286,
  -9.570061600833877E-4,
  -0.0032031199216315752,
  -0.3695577117467811,
 },
 {
  6.331591200641909E-4,
  -6.072079117545927E-4,
  4.488028888666921E-4,
  0.001502152790834118,
  0.25433855969250857,
 },
 {
  -0.018490756271695635,
  0.0177328781134214,
  -0.013106823496794245,
  -0.043868816317997744,
  -0.7212089029784733,
 },
 {
  0.00867152707914002,
  -0.008316108356639,
  0.006146648260565575,
  0.020572962135336867,
  0.4979865101240707,
 },
 {
  7.39630250867824E-4,
  -7.093151245368548E-4,
  5.242729398717688E-4,
  0.0017547526527199064,
  0.2698594614725992,
 },
 {
  -3.468610831656002E-4,
  3.3264433426555945E-4,
  -2.458659304226226E-4,
  -8.229184854134733E-4,
  -0.1856984479771845,
 },
 {
  -0.014401266260283584,
  0.013811003483045665,
  -0.010208065707556153,
  -0.034166612497403534,
  -0.3279841271357853,
 },
 {
  0.0067536972806847145,
  -0.0064768843920490005,
  0.004787230814578058,
  0.01602296310223062,
  0.22708646648144698,
 },
 {
  5.760506504113423E-4,
  -5.524401393218255E-4,
  4.083226283022453E-4,
  0.0013666644998961387,
  0.12365500050329088,
 },
 {
  -2.701478912273881E-4,
  2.5907537568195955E-4,
  -1.9148923258312196E-4,
  -6.409185240892237E-4,
  -0.08511516559388549,
 },
 {
  0.007889389342590137,
  -0.007566027995059798,
  0.0055922446919655444,
  0.01871736162901237,
  0.24063271651627594,
 },
 {
  -0.0036998515537664083,
  0.0035482062321659734,
  -0.002622569924507979,
  -0.00877779717774373,
  -0.1663312858221431,
 },
 {
  -3.155755737036049E-4,
  3.026411198023914E-4,
  -2.2368978767862136E-4,
  -7.486944651604934E-4,
  -0.09030634861745682,
 },
 {
  1.4799406215065607E-4,
  -1.419282492866387E-4,
  1.0490279698031899E-4,
  3.511118871097486E-4,
  0.062149536970008565,
 },
 {
  -0.02782253981068222,
  0.026682181086549964,
  -0.019721482083961437,
  -0.06600821894577232,
  -0.8533273005003168,
 },
 {
  0.013047811773285452,
  -0.01251302285438205,
  0.00924869504627157,
  0.03095557854008632,
  0.5898250075352346,
 },
 {
  0.0011129015924272866,
  -0.0010672872434619963,
  7.888592833584559E-4,
  0.002640328757830887,
  0.3202186765374369,
 },
 {
  -5.219124709314172E-4,
  5.005209141752811E-4,
  -3.6994780185086205E-4,
  -0.0012382231416034504,
  -0.22037637462248372,
 },
 {
  0.015241913113678085,
  -0.01461719485610998,
  0.010803942359039743,
  0.03616102429203179,
  0.6252357629541492,
 },
 {
  -0.007147931667104204,
  0.006854960346313644,
  -0.00506667641665312,
  -0.016958273461090766,
  -0.4316369688767114,
 },
 {
  -6.096765245471222E-4,
  5.84687794244398E-4,
  -4.3215769436158886E-4,
  -0.0014464409716812687,
  -0.23382609302035745,
 },
 {
  2.8591726668416765E-4,
  -2.741984138525453E-4,
  2.0266705666612445E-4,
  6.783309384436294E-4,
  0.16089958576874594,
 },
 {
  0.011870950319224412,
  -0.01138439726359465,
  0.008414499022490212,
  0.028163506750196184,
  0.2844571927191722,
 },
 {
  -0.005567066356601792,
  0.005338889751203007,
  -0.003946109886409202,
  -0.013207713510436828,
  -0.19688594381271154,
 },
 {
  -4.748380127689756E-4,
  4.5537589054378514E-4,
  -3.3657996089960787E-4,
  -0.0011265402700078452,
  -0.10714849066960343,
 },
 {
  2.2268265426407134E-4,
  -2.1355559004811994E-4,
  1.5784439545636782E-4,
  5.283085404174723E-4,
  0.07375077268012249,
 },
 {
  -0.006503216261835982,
  0.00623666980527359,
  -0.004609682073190289,
  -0.015428703697933558,
  -0.20864518481158237,
 },
 {
  0.00304978417796446,
  -0.002924783081093821,
  0.0021617819377719974,
  0.007235530010065392,
  0.14418595896616604,
 },
 {
  2.601286504734388E-4,
  -2.4946679221094317E-4,
  1.8438728292761127E-4,
  6.171481479173413E-4,
  0.07824941397639225,
 },
 {
  -1.2199136711857819E-4,
  1.1699132324375264E-4,
  -8.647127751087975E-5,
  -2.894212004026152E-4,
  -0.05385048876470649,
 }
};
/*	double [][] itDrv= 
	    sPoly.getIterateDeterministicWDrvWithWts().evaluate(aState);

	int ii;int jj;


	for(ii=0;ii<5*32;ii++){
	for(jj=0;jj<5;jj++){
	    assertEquals(rghtDrv[ii][jj],itDrv[ii][jj],1e-8);
	}}*/
    }
}