package ImageTools;

public class TestRead {

    /**
     * A class to test the functionality of the class ReadImage
     *
     */

    public static void main(String[] args) {
        ReadImage test = new ReadImage("Numbers/");

        Dataset ds;
        while((ds = test.getNext()) != null){
            System.out.println(ds.getSolution()+" "+ds.getSearch());
        }

    }

}
