package se.simonevertsson.gpu.query.relationship.join;

import com.nativelibs4java.opencl.CLBuffer;
import com.nativelibs4java.opencl.CLMem;
import junit.framework.TestCase;
import org.bridj.Pointer;
import org.neo4j.graphdb.Relationship;
import se.simonevertsson.MockHelper;
import se.simonevertsson.MockQuery;
import se.simonevertsson.gpu.query.relationship.search.CandidateRelationships;
import se.simonevertsson.gpu.query.relationship.join.PossibleSolutions;
import se.simonevertsson.gpu.query.QueryUtils;
import se.simonevertsson.gpu.query.relationship.join.SolutionValidator;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by simon on 2015-06-25.
 */
public class SolutionValidatorTest extends TestCase {
    private MockQuery mockQuery;
    private HashMap<Integer, CandidateRelationships> candidateRelationshipsHashMap;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        boolean[] inputCandidateIndicators = {
                true, true, false, false,
                false, true, true, false,
                false, true, true, true,
        };



        this.mockQuery = MockHelper.generateTriangleMockQuery();
        mockQuery.bufferContainer.queryBuffers.candidateIndicatorsBuffer =
                mockQuery.queryKernels.context.createBuffer(CLMem.Usage.Output, Pointer.pointerToBooleans(inputCandidateIndicators), true);
        mockQuery.bufferContainer.queryBuffers.candidateIndicatorsPointer =
                mockQuery.bufferContainer.queryBuffers.candidateIndicatorsBuffer.read(mockQuery.queryKernels.queue);

        this.candidateRelationshipsHashMap = new HashMap<Integer, CandidateRelationships>();

        /******* Relationship candidates 1 *********/

        Relationship queryRelationship = mockQuery.queryContext.queryGraph.relationships.get(0);
        CandidateRelationships candidateRelationships = new CandidateRelationships(queryRelationship, mockQuery.queryContext, this.mockQuery.queryKernels);

        int[] candidateStartNodes = {
                0,1
        };
        int[] candidateRelationshipEndNodeIndices = {
                0,2,3
        };
        int[] candidateRelationshipEndNodes = {
                1,2,2
        };

        int[] candidateRelationshipIndices = {
                0,1,2
        };

        CLBuffer<Integer>
                candidateStartNodesBuffer = mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateStartNodes), true);
        CLBuffer<Integer> candidateRelationshipEndNodeIndicesBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateRelationshipEndNodeIndices), true);
        CLBuffer<Integer> candidateRelationshipEndNodesBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateRelationshipEndNodes), true);
        CLBuffer<Integer> candidateRelationshipIndicesBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateRelationshipIndices), true);



        candidateRelationships.setCandidateStartNodes(candidateStartNodesBuffer);
        candidateRelationships.setCandidateEndNodeIndices(candidateRelationshipEndNodeIndicesBuffer);
        candidateRelationships.setEndNodeCount(candidateRelationshipEndNodeIndices[candidateRelationshipEndNodeIndices.length - 1]);
        candidateRelationships.setCandidateEndNodes(candidateRelationshipEndNodesBuffer);
        candidateRelationships.setCandidateRelationshipIndices(candidateRelationshipIndicesBuffer);

        candidateRelationshipsHashMap.put((int) queryRelationship.getId(), candidateRelationships);

        /******* Relationship candidates 2 *********/

        queryRelationship = mockQuery.queryContext.queryGraph.relationships.get(1);
        candidateRelationships = new CandidateRelationships(queryRelationship, mockQuery.queryContext, this.mockQuery.queryKernels);

        candidateStartNodes =  new int[] {
                0,1
        };
        candidateRelationshipEndNodeIndices =  new int[] {
                0,2,4
        };
        candidateRelationshipEndNodes = new int[] {
                1,2,2,3
        };

        candidateRelationshipIndices = new int[] {
                0,1,2,3
        };


        candidateStartNodesBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateStartNodes), true);
        candidateRelationshipEndNodeIndicesBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateRelationshipEndNodeIndices), true);
        candidateRelationshipEndNodesBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateRelationshipEndNodes), true);
        candidateRelationshipIndicesBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateRelationshipIndices), true);

        candidateRelationships.setCandidateStartNodes(candidateStartNodesBuffer);
        candidateRelationships.setCandidateEndNodeIndices(candidateRelationshipEndNodeIndicesBuffer);
        candidateRelationships.setEndNodeCount(candidateRelationshipEndNodeIndices[candidateRelationshipEndNodeIndices.length - 1]);
        candidateRelationships.setCandidateEndNodes(candidateRelationshipEndNodesBuffer);
        candidateRelationships.setCandidateRelationshipIndices(candidateRelationshipIndicesBuffer);

        candidateRelationshipsHashMap.put((int) queryRelationship.getId(), candidateRelationships);

        /******* Relationship candidates 3 *********/

        queryRelationship = mockQuery.queryContext.queryGraph.relationships.get(2);
        candidateRelationships = new CandidateRelationships(queryRelationship, mockQuery.queryContext, this.mockQuery.queryKernels);

        candidateStartNodes =  new int[] {
                1,2
        };


        candidateRelationshipEndNodeIndices =  new int[] {
                0,2,3
        };
        candidateRelationshipEndNodes = new int[] {
                2,3,3
        };
        candidateRelationshipIndices = new int[] {
                2,3,4
        };


        candidateStartNodesBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateStartNodes), true);
        candidateRelationshipEndNodeIndicesBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateRelationshipEndNodeIndices), true);
        candidateRelationshipEndNodesBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateRelationshipEndNodes), true);
        candidateRelationshipIndicesBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(candidateRelationshipIndices), true);


        candidateRelationships.setCandidateStartNodes(candidateStartNodesBuffer);
        candidateRelationships.setCandidateEndNodeIndices(candidateRelationshipEndNodeIndicesBuffer);
        candidateRelationships.setEndNodeCount(candidateRelationshipEndNodeIndices[candidateRelationshipEndNodeIndices.length - 1]);
        candidateRelationships.setCandidateEndNodes(candidateRelationshipEndNodesBuffer);
        candidateRelationships.setCandidateRelationshipIndices(candidateRelationshipIndicesBuffer);

        candidateRelationshipsHashMap.put((int) queryRelationship.getId(), candidateRelationships);
    }

    public void testValidateSolutionWithRelationship1WhenRelationship2And0Visited() throws IOException {
        // Given

        /* Relationship 2 and 0 visited */
        int[] possibleSolutionElements = {
                0,1,2, 0,1,3, 0,2,3, 1,2,3
        };

        int[] oldPossibleSolutionRelationships = {
                0,-1,2, 0,-1,3, 1,-1,4, 2,-1,4
        };

        CLBuffer<Integer> possibleSolutionElementsBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(possibleSolutionElements), true);

        CLBuffer<Integer> oldPossibleSolutionRelationshipsBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(oldPossibleSolutionRelationships), true);

        PossibleSolutions possibleSolutions = new PossibleSolutions(possibleSolutionElementsBuffer, oldPossibleSolutionRelationshipsBuffer, this.mockQuery.queryContext, this.mockQuery.queryKernels.queue);

        CandidateRelationships candidateRelationships = this.candidateRelationshipsHashMap.get(1);

        SolutionValidator solutionValidator = new SolutionValidator(this.mockQuery.queryKernels, this.mockQuery.queryContext);

        // When
        CLBuffer<Boolean> result =  solutionValidator.validateSolutions(possibleSolutions, candidateRelationships);
                Pointer<Boolean> solutionElementsResultPointer =result.read(this.mockQuery.queryKernels.queue);

        int solutionElementsSize = possibleSolutionElements.length/mockQuery.queryContext.queryNodeCount;
        System.out.println(Arrays.toString(QueryUtils.pointerBooleanToArray(solutionElementsResultPointer, solutionElementsSize)));

        // Then
//        int[] expectedValidRelationships = {
//                1, -1, -1, 3
//        };

        boolean[] expectedValidRelationships = {
                true, false, false, true
        };


        for(int i = 0; i < expectedValidRelationships.length; i++) {
            assertEquals(expectedValidRelationships[i], (boolean) solutionElementsResultPointer.get(i));
        }
    }

    public void testValidateSolutionWithRelationship2WhenRelationship0And1Visited() throws IOException {
        // Given

        /* Relationship 0 and 1 visited */
        int[] possibleSolutionElements = {
                0,1,2, 0,2,1, 1,2,3
        };

        int[] oldPossibleSolutionRelationships = {
                0,1,-1, 1,0,-1, 2,3,-1
        };

        CLBuffer<Integer> possibleSolutionElementsBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(possibleSolutionElements), true);

        CLBuffer<Integer> oldPossibleSolutionRelationshipsBuffer =
                mockQuery.queryKernels.context.createIntBuffer(CLMem.Usage.Input, IntBuffer.wrap(oldPossibleSolutionRelationships), true);

        PossibleSolutions possibleSolutions = new PossibleSolutions(possibleSolutionElementsBuffer, oldPossibleSolutionRelationshipsBuffer, this.mockQuery.queryContext, this.mockQuery.queryKernels.queue);

        CandidateRelationships candidateRelationships = this.candidateRelationshipsHashMap.get(2);

        SolutionValidator solutionValidator = new SolutionValidator(this.mockQuery.queryKernels, this.mockQuery.queryContext);

        // When
        CLBuffer<Boolean> result =  solutionValidator.validateSolutions(possibleSolutions, candidateRelationships);
        Pointer<Boolean> solutionElementsResultPointer =result.read(this.mockQuery.queryKernels.queue);

        int solutionElementsSize = possibleSolutionElements.length/mockQuery.queryContext.queryNodeCount;
        System.out.println(Arrays.toString(QueryUtils.pointerBooleanToArray(solutionElementsResultPointer, solutionElementsSize)));

        // Then
//        int[] expectedValidationIndicators = {
//                2, -1, 4
//        };

        boolean[] expectedValidationIndicators = {
                true, false, true
        };


        for(int i = 0; i < expectedValidationIndicators.length; i++) {
            assertEquals(expectedValidationIndicators[i], (boolean) solutionElementsResultPointer.get(i));
        }
    }
}
