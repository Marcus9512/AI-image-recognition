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
