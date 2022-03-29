# Hosts roles in hadoop cluster

## Master
### DFS
- NameNode: 1
### YARN
- ResourceManager: 1

## Workers
### DFS
- DataNode: *
### YARN
- NodeManager: *
- WebAppProxy: 1
- JobHistory: 1