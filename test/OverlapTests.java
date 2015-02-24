package test;
import org.pac.games.physics.Rectangle;
import java.util.LinkedList;

public class OverlapTests implements TestFactory{
    public Iterable<Test> getTests(){
        LinkedList<Test> tests = new LinkedList<Test>();
        tests.add(new OverlapTest(
            "Enclosed", true,
            1, 4, 0, 15, 4
        ));
        tests.add(new OverlapTest(
            "Right Hit", true,
            1, 14, 0, 5, 4
        ));
        tests.add(new OverlapTest(
            "Right Miss", true,
            5, 10, 0, 1, -4
        ));
        tests.add(new OverlapTest(
            "Enclosing", true,
            0, 15, 1, 4, 4
        ));
        tests.add(new OverlapTest(
            "Left Hit", true,
            0, 5, 1, 14, 4
        ));
        tests.add(new OverlapTest(
            "Left Miss", true,
            0, 1, 5, 10, -4
        ));
        tests.add(new OverlapTest(
            "Enclosed", false,
            1, 4, 0, 15, 4
        ));
        tests.add(new OverlapTest(
            "Bottom Hit", false,
            1, 14, 0, 5, 4
        ));
        tests.add(new OverlapTest(
            "Bottom Miss", false,
            5, 10, 0, 1, -4
        ));
        tests.add(new OverlapTest(
            "Enclosing", false,
            0, 15, 1, 4, 4
        ));
        tests.add(new OverlapTest(
            "Top Hit", false,
            0, 5, 1, 14, 4
        ));
        tests.add(new OverlapTest(
            "Top Miss", false,
            0, 1, 5, 10, -4
        ));
        return tests;
    }
}
class OverlapTest implements Test{
    private String name;
    private boolean isX;
    //dim = x|y    size=width|height
    private int dim1, size1, dim2, size2;
    private int expected;
    public OverlapTest(
        String name, boolean isX,
        int dim1, int size1, 
        int dim2, int size2, 
        int expected
    ){
        this.name = name;
        this.isX = isX;
        this.dim1 = dim1;
        this.size1 = size1;
        this.dim2 = dim2;
        this.size2 = size2;
        this.expected = expected;
    }
    public String getName(){
        String fullName = "OverlapTest ";
        fullName+= isX?"X":"Y";
        fullName+= " "+name;
        return fullName;
    }
    public boolean runTest(){
        Rectangle r1 = new Rectangle(dim1, dim1, size1, size1);
        Rectangle r2 = new Rectangle(dim2, dim2, size2, size2);
        double overlap;
        if(isX)
            overlap = r1.getXOverlap(r2);
        else
            overlap = r1.getYOverlap(r2);
        return Math.abs(overlap-expected)<.00001;
    }
}
