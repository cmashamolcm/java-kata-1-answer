##Kubernetes:
****

1. What is issue without container orchestration?
    * maintainability issues
    * failure fix difficulties
    * manual effort
    * Load based scaling is necessary to save resources
    * <b>Scaling is the key.</b> VM scaling was difficult due to excess resource use for OS. But containers are good as it uses shared native OS.
    
2. K8 and Docker Swarm solves such issues by managing the containers.
   But why cannot the docker engine handle all these scaling things as well?
   Docker engine manages the life cycle of containers. It is not capable of to scale up or down containers or fix issues of container.
3. Kubernetes                           vs                                Docker Swarm:
    * Auto Scalable                                                         * No auto scaling
    * Need to configure load balancer                                       * Auto load-balancing possible
    * Concept of pods is there for container grouping                       * No grouping
    * Shared volume between container in a pod                              * Shared volum between any containers
    * Own logging support                                                   * Need ELK or 3rd party support for logging
    
4. Why we need Kubernetes?
    * Manage the docker containers in communicating with external world, load balance, distribution etc.
    * To deploy, manage and scale our containers
5. Features of Kubernetes:
    * Auto-scaling
    * Auto rollout and rollback
    * Self-healing - restart and reschedule dies containers etc
    * Horizontal Scaling and load balancing
6. Architecture:
    



                                                                                                                Image reregistry     
                                                                                                                        |
    Command line interface to                                       |---------------------------------------------------|                                                           |---------------------------------------------------
    communicate with master--------------------->                   | Master Node                                       |                                                           |worker node
                                                     API----------->|                                                   |                                                           |    - where actual containers resides inside pods. Workedr node has multiple pods in it.
     UI to interact with master----------------->                   |(the main node handling the admin activities                                                                   |    - Extrenal world connects to worker nodes where apps are residing
                                                                    |such as managing the worked nodes)                                                                             |    - managed by master nodes                                                                                                      |------->controller manager    
                                                                    |Can have multiple master nodes whcih ensures availability------------------<---------------------------------->|has                                                                                                                                |
                                                                    |                                                                                                               |    - kubelet    - command line tool to interact with master node API service     -------------------------------kubectl---->API server in master
                                                                    |Has                                                                                                            |    - kube-proxy - netwrok proxy as well as load balancer for services in single worker node                                       |
                                                                    |   -api server - handles all REST commands to control cluster                                                  |                                                                                                                                   |-------->scheduler    
                                                                    |   -controller manager - manages things for the cluster like life cycle management etc.                        |
                                                                    |   -Scheduler  - scheduling of pos and services                                                                |holds multiple pods which are having group of containers residing in docker engine
                                                                    |   - etcd  - key-value store holding cluser state info, shared config, 
                                                                    |               service discovery



7. Setup MiniKube in local:
   * brew install kubectl
   * install docker
   * install minikube
   * "minikube version"
     "kubectl version --client"
   * minicube start 
     (while starting, Kubernetes looks for virtualization tools like Docker by default (mandatory to have docker or hyper-v etc)
     It is mandatory to have docker up and running)
     (We can specify other containerization or hypervisor tools by "minikube start --driver=docker" )
   * This will start the Kubernetes cluster with master node and workers, Kubelet, controller management, API server etc.
     * minikube status -----gives us the state of Kubernetes cluster
     * minikube dashboard  -----will open up a dashboard for us in browser.
    
8. Run Spring boot Application In Minikube:
    1. Create a simple spring boot application
    2. Dockerize it with Dockerfile and dockerfile-maven plugin or can do it directly with docker build command
    3. minukube start
    4. minikube delete if any error comes on restart and try again with minikube start.
    5. minikube dashboard
    6. eval $(minikube docker-env)   ----------this helps the current command console to be updated in such a way that the in built docker engine of minikube will be running when we do docker build etc.
    7. Go to project folder
    8. Run "docker build -t image-name:version ."
    9. When we type docker images, will be able to see the new image along with k8 images in registry
    10.To view the system folders of minikube, we can use minikube ssh
    11.Once docker image is ready, 
       create a deployment..which will build an environment to start a k8 container runtime in which a pod will be residing. That pod will be having one docker container running
       "kubectl create deployment springboot-docker-kubernetes --image=springboot-docker-kubernetes:2.4.4"
       In kube dashboard, we will be able to view the services and pods
    12.kubectl get deployments 
    13.kubectl expose deployment springboot-docker-kubernetes --type=LoadBalancer --port 8082 -----runs the app and exposes it in 8082 port
    14.kubectl get services   
    15.minikube service springboot-docker-kubernetes
       will start the service and load it in some random port in browser.
       Eg: http://127.0.0.1:57760/
   