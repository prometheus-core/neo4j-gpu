package se.simonevertsson.gpu;

import com.nativelibs4java.opencl.CLBuffer;
import com.nativelibs4java.opencl.CLQueue;

import java.util.Arrays;

/**
 * Created by simon.evertsson on 2015-07-27.
 */
public class PossibleSolutions {

    private final int solutionCount;
    private final CLQueue clQueue;
    private final QueryContext queryContext;
    private CLBuffer<Integer> solutionElements;

    private CLBuffer<Integer> solutionRelationships;

    public PossibleSolutions(CLBuffer<Integer> solutionElements, CLBuffer<Integer> solutionRelationships, QueryContext queryContext, CLQueue queue) {
        this.solutionElements = solutionElements;
        this.solutionRelationships = solutionRelationships;
        this.queryContext = queryContext;
        this.clQueue = queue;

        this.solutionCount = (int) (this.solutionElements.getElementCount()/this.queryContext.queryNodeCount);
    }


    public CLBuffer<Integer> getSolutionElements() {
        return solutionElements;
    }

    public CLBuffer<Integer> getSolutionRelationships() {
        return solutionRelationships;
    }

    public int getSolutionCount() {
        return solutionCount;
    }

    public String toString() {
        int solutionElementCount = this.queryContext.queryNodeCount;
        int solutionRelationshipsCount = this.queryContext.queryRelationshipCount;
        StringBuilder builder = new StringBuilder();
        builder.append("Solutions: ");
        int[] solutionElements = QueryUtils.pointerIntegerToArray(this.solutionElements.read(this.clQueue), (int) this.solutionElements.getElementCount());
        int[] solutionRelationships = QueryUtils.pointerIntegerToArray(this.solutionRelationships.read(this.clQueue), (int) this.solutionRelationships.getElementCount());

        int elementOffset = 0;
        int relationshipOffset = 0;
        for(int i = 0; i < this.solutionCount; i++) {
            builder.append("{\n");
            builder.append(Arrays.toString(Arrays.copyOfRange(solutionElements, elementOffset, elementOffset+solutionElementCount)));
            builder.append('\n');
            builder.append(Arrays.toString(Arrays.copyOfRange(solutionRelationships, relationshipOffset, relationshipOffset + solutionRelationshipsCount)));
            builder.append("}\n");
            elementOffset += solutionElementCount;
            relationshipOffset += solutionRelationshipsCount;
        }

//        builder.append("Solution elements: ");
//        builder.append(Arrays.toString(QueryUtils.pointerIntegerToArray(this.solutionElements.read(this.clQueue), solutionElementCount)));
//        builder.append('\n');
//        builder.append("Solution relationships");
//        builder.append(Arrays.toString(QueryUtils.pointerIntegerToArray(this.solutionRelationships.read(this.clQueue), solutionRelationshipsCount)));
        return builder.toString();
    }
}
