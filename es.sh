
curl -X DELETE localhost:9200/example/post

curl -XPUT http://localhost:9200/example/post/1 -d '{                                                                                       
    "name": "uno",                                                                                                                          
    "description": "uno"                                                                                                                    
}'

curl -XPUT http://localhost:9200/example/post/2 -d '{                                                                                       
    "name": "dos",                                                                                                                          
    "description": "dos"                                                                                                                    
}'

curl -XPUT http://localhost:9200/example/post/3 -d '{                                                                                       
    "name": "tres",                                                                                                                          
    "description": "tres"                                                                                                                    
}'

