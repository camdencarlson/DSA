public class TestDriver {
    public static void main(String[] args) {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addToFront("Camden");
        list.addToFront("Joseph");
        System.out.println(list.getHead().getNext().getData());
    }
}
