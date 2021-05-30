### Jenkins:
****

1. Install Jenkins
2. Specify in configuration, maven path and JDK path
3. Create a maven project
4. Specify the git repo details
5. Task to execute (clean deploy)
6. Save
7. trigger build
8. It will get auto triggered if we configured that way.
9. Jenkins JDK version should be appropriate with the actual github project version. Else, error comes.
10. It is mandatory to have distributionManagement in pom.xml holding the details of repository to deploy the jar.
11. Auto-build may get locked if token based git access isnot configured in Jenkins github config.
12. Jenkins has master-slave concept
13. Jenkins Pipeline:
        * Continuous integration instructions
        * It is a plugin
        * Uses Groovy to write the pipeline code
        * Different stages like build, deploy, test etc will be there as well as the tasks to be performed in each stage.
        * Best suited for situations where there are many complex long-running activities involved in project build.
        * While configuring the pipeline project ensure to add github repo and instead of gicing scripts,
          select "pipeline script from SCM" so that Jenkinsfile will be taken from project folder.
        * When we setup a pipeline job where read from SCM is given, error comes if a Jenkinsfile is not present.
    * Node: Where the job is running
    * Pipeline: Series of instructions
    * Stages: Each step in pipeline
    * Step: Each task in a stage