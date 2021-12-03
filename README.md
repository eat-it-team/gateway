# gateway
Gateway Application
## Поставка на k8s

1. Создать докер образ локально командой `docker build -t <container-name> . ` - `container_name` имя контейнера.
Например:
```
docker build -t digital-twin-gateway . 
```

2. Протегать контейнер командой `docker tag <container-name> <docker-hub>/<container-name-for-docker-hub>:<version>`- 
где `<container-name>`  - имя контейнера из 1-го шага, `<docker-hub>` - логин для docker hub, `<container-name-for-docker-hub>` - 
имя контейнера для публикации в докер-хабе, `<version>` - версия. Например:
```
docker tag digital-twin-gateway vladimirkurovx5/digital-twin-gateway:1.0.9
```
3. Отправить контейнер в докер хаб командой `docker push <docker-hub>/<container-name-for-docker-hub>:<version>`
Например:
```
docker push vladimirkurovx5/digital-twin-gateway:1.0.8
```
4. В файле [kube](kube/gateway.yaml) обновить версию образа докера
5. Выполнить команду 
```
kubectl apply -f <path-to-kube-directory> --kubeconfig=<path-to-kube-config>
```
где необходимо указать путь до директории [kube](kube) и путь до файла с конфигами для подключения к стенду
