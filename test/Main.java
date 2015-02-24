package test;

public class Main{
    public static void main(String[] args){
        TestFactory[] testFactories = new TestFactory[]{
            new OverlapTests()
        };
        int failed = 0;
        for(TestFactory factory:testFactories){
            for(Test test:factory.getTests()){
                System.out.print(test.getName()+"... ");
                if(test.runTest())
                    System.out.println("passed.");
                else{
                    System.out.println("FAILED!!!");
                    failed++;
                }
            }
        }
        if(failed==0){
            System.out.println("All tests passed.");
            System.exit(0);
        }
        else{
            System.out.println(""+failed+" TESTS FAILED!!!");
            System.exit(1);
        }
    }
}
