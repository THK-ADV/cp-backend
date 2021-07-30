img_name=cp-backend

clear_images() {
  docker-compose stop &&
    docker-compose down &&
    docker image rm ${img_name}
  docker image prune -f
}

build_images() {
  docker image rm ${img_name}
  docker build -t ${img_name} .
}

case $1 in
up)
  clear_images &&
    build_images &&
    docker-compose up -d &&
    exit 0
  ;;
stop)
  docker-compose stop &&
    exit 0
  ;;
*)
  echo expected up or down, but was $1
  exit 1
  ;;
esac
