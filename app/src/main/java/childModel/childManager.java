package childModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class childManager implements Iterable<Child>{
    public int size;
    private List<Child> children = new ArrayList<>();

    //Singleton support
    private static childManager instance;
    private childManager() {

    }

    public static childManager getInstance() {
        if(instance == null) {
            instance = new childManager();
        }

        return instance;
    }

    public void addChild(Child child) {
        children.add(child);
        size++;
    }

    public List<Child> getList() {
        return children;
    }

    @Override
    public Iterator<Child> iterator() {
        return children.iterator();
    }

    public Child get(int i) {
        return children.get(i);
    }

    public int size() {
        return size;
    }
}
