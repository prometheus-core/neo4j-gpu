__kernel void count_candidate_relationships(
    int q_start_node,
    int q_end_node,
    int q_relationship_type,
    __global int* d_relationships,
    __global int* d_relationship_types,
    __global int* d_relationship_indices,

    __global int* candidate_relationship_counts,
    __global int* candidates_array,
    __global bool* candidate_indicators,
    int d_node_count)
{
    int workset_index = get_global_id(0);
    candidate_relationship_counts[workset_index] = 0;

    int candidate_start_node = candidates_array[workset_index];

    int c_relationship_start_index = d_relationship_indices[candidate_start_node];
    int c_relationship_end_index = d_relationship_indices[candidate_start_node+1];

    for(int i = c_relationship_start_index; i < c_relationship_end_index; i++) {
        int candidate_end_node = d_relationships[i];
        int candidate_relationship_type = d_relationship_types[i];
        bool valid_relationship = q_relationship_type != -1 ? candidate_relationship_type == q_relationship_type : true;
        if(valid_relationship && candidate_indicators[q_end_node*d_node_count + candidate_end_node]) {
            candidate_relationship_counts[workset_index] += candidate_indicators[q_end_node*d_node_count + candidate_end_node];
        }        
    }
}