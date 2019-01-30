/**
 * A class to test the functionality of the class ReadImage
 *
 * @author Marcus Jonsson Ewerbring @ Jonas Johansson
 * @verion 1.0
 * @since 2019-01-03
 */
package ImageTools;

public class TestRead {

    public static void main(String[] args) {
        ReadImage test = new ReadImage("Numbers/");

        Dataset ds;
        while((ds = test.getNext()) != null){
            System.out.println(ds.getSolution()+" "+ds.getSearch());
        }

    }

}
