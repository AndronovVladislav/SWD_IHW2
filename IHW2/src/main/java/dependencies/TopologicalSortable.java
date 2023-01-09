package dependencies;

import java.util.List;

public interface TopologicalSortable {
    String sortingIsPossible();

    List<Vertex> topologicalSorting();
}
