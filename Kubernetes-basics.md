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
       
    16. kubectl create deployment demo --image=springboot-docker-kubernetes:2.4.4 --dry-run -o=yaml > deployment.yaml
        will generate a yaml file to setup kubernetes Deployment.
    17. After that,
        Add imagePullPolicy: Never also if we want to take local image
    18. kubectl get all ---will show all services, pods and deployments.
    19. We have to create a service now for this pod deployed.
        kubectl expose deployment demo  --type=LoadBalancer --port 8082
        It will create a service for us which makes the pod expose 8082 port for internal communication with service.
    20. minikube service demo
        will load the service in random port in browser.
       

9. Commonly used Terms:
    * Containers:
      The basic block where an application resides in.
      Provided by docker or such hypervisor drivers attached to the k8
    * Pods:
      Smallest deployable unit.
      Logical group of running containers in a K8 cluster
      Updates happens to entire pod usually
    * Container runtime:
      Where the pods are residing in.
      Can be considered like a JVM kind of thing in which the pos will be living in
    * Nodes:
      Machine where K8 resides in
    * A set of K8 nodes working together is called K8 cluster
    
    * Workload:
      The actual app running in k8. It can be in a single node or many nodes, in many pods and containers.
      Together is called workload
      
    * Deployment: 
        The declarative updates for pos and replicas
        It is the one which controls the life of pods
        When we create deployment we specify a port. That is local to the pod.
        That is the port through which pod exposes the apps to service.
        There will be another port in service randomly allocated to expose this to external world.
        There will be an app labels (called selector--similar to service ids in eureka) based on which each apps in same pod is uniquely identified.
        So, multiple apps if present in a pod can be exposed through different ports to the service. 
      
    *  Service: 
        It can treat as group of pods.
        The pods associated with a service will be exposed through dynamic service ports to external world
        Abstract way of exposing apps residing in pods.
        Service has it's own ports, network IP, lifecycle etc.
        Pods also has its own IP, port etc.
        External world is exposed by services.
        Pod lifecycle is independent of service.
        So, even if something happens to pod, just a deployment of pod will be enough to make the service come back.
        The external users and apps will be unaffected as there will be any change for IP or port once created.
        The pod gone down comes back and ties to the same service and works smoothly.
    * Default protocol between pod and services is tcp.
        pods----------tcp---------service

Reference: https://www.bogotobogo.com/DevOps/DevOps-Kubernetes-1-Running-Kubernetes-Locally-via-Minikube.php

   