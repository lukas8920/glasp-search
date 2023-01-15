sudo docker build -t glasp-search-image-1.18 .

sudo docker run -d -p 8091:8091 -v /home/batlukas/glasp-search/app.properties:/local/glasp/app.properties --name glasp-search-container-1.17 --network bridge-network glasp-search-image-1.17

