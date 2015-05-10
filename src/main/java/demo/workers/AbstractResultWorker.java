package demo.workers;

import org.hibernate.search.query.facet.Facet;

import java.util.List;

public abstract class AbstractResultWorker {

   protected String stringResultsFromFacets(List<Facet> facets,
                                         String electionType) {
      StringBuilder builder = new StringBuilder();
      builder.append("{\"electionType\": \"");
      builder.append(electionType);
      builder.append("\", \"candidates\": [");

      for (Facet f : facets) {
         builder.append("{\"name\": \"");
         builder.append(f.getValue());
         builder.append("\", \"votes\": \"");
         builder.append(f.getCount());
         builder.append("\"}, ");
      }
      // Remove the trailing comma.
      builder.deleteCharAt(builder.length() - 2);
      builder.append("]}");

      return builder.toString();

   }

}
