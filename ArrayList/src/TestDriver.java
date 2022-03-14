public class TestDriver {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.removeFromFront();
        list.addAtIndex(0, "2a");   // 2a
//
        list.addAtIndex(0, "1a");   // 1a, 2a
//////
//        list.addAtIndex(2, "4a");   // 1a, 2a, 4a
//////
//        list.addAtIndex(2, "3a");   // 1a, 2a, 3a, 4a
//        list.addAtIndex(0, "0a");   // 0a, 1a, 2a, 3a, 4a

        for (int i = 0; i < 6; i++) {
            System.out.print(list.get(i) + " ");
        }
    }
}
