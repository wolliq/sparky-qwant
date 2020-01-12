# sparky-qwant

## Launch ElasticSearch engine (in local)

See https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started-install.html

```
curl -L -O https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.5.1-darwin-x86_64.tar.gz
tar -xvf elasticsearch-7.5.1-linux-x86_64.tar.gz
cd elasticsearch-7.5.1/bin

./elasticsearch
./elasticsearch -Epath.data=data2 -Epath.logs=log2
./elasticsearch -Epath.data=data3 -Epath.logs=log3

curl -X GET "localhost:9200/_cat/health?v&pretty"
```