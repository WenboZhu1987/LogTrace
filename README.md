# logTracer 

Ingest log records to determine the overall health of the system. 
  
  
#### Description
1. Assume a log Entity has following attributes: time, app, msg, spanId, parentSpanId, component, level, env, traceId and list of child log Entities
2. Read the log-data file then restores each log object into an ArrayList. 
3. Sort the ArrayList.
4. Convert Each object from the ArrayList to a logEntity and store every logEntity into a new ArrayList.
5. Search the new ArrayList to see if any logEntity has a parent spanId. If yes, then add the logEntity to its parent's subLogEntity list.
6. Print the List of log entities. Add a tab at the beginning of any child log entity.




  
