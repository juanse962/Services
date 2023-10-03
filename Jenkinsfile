#!groovy
import java.text.SimpleDateFormat

def dateFormat = new SimpleDateFormat("yyyyMMddHHmm")
def date = new Date()
def timestamp = dateFormat.format(date).toString()
def CORREOS = "jcordobap@choucairtesting.com"

pipeline {
    agent any
    stages {
        stage('Obtener Fuentes')
                {
                    steps
                            {
                                checkout([$class: 'GitSCM', branches: [[name: "master"]],
                                          wdoGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [
                                        [credentialsId: "AzureGITRepos", url: "https://DEV-FormacionCH@dev.azure.com/DEV-FormacionCH/Pilot/_git/ProyectoBaseServicios"]
                                ]])
                            }
                }

        stage('SonarQube analysis')
                {
                    steps {
                        script {    
                            scannerHome = tool 'SonarQubeScanner'
                            //mismo nombre del servidor configurado en las Global Tools Jenkins
                        }
                        withSonarQubeEnv('sonarQube')//mismo nombre del servidor configurado en la configuracion del sistema jenkins
                                {
                                    bat 'sonar-scanner'
                                }
                    }
                }

        stage("Quality Gate") {
            steps {
                script {
                    try {
                        timeout(time: 1, unit: 'MINUTES') {
                            waitForQualityGate abortPipeline: true
                            currentBuild.result = 'SUCCESS'
                        }
                    }
                    catch (ex) {
                        currentBuild.result = 'FAILURE'
                    }
                }
            }
        }

        stage('Ejecutar Pruebas') {
            steps {
                script {
                    try {
                        //bat ("gradle clean test -DRunner=\"${Runner}\" aggregate") //Ejecución en agente Windows con parametro jenkins
                        //sh ("gradle clean test -DRunner=\"${Runner}\" aggregate") //Ejecución en agente Linux con parametro jenkins
                        bat("gradle clean test aggregate") //Ejecución en agente windows sin parametro jenkins
                        echo 'Test Ejecutados sin Fallo'
                        currentBuild.result = 'SUCCESS'
                    }
                    catch (ex) {
                        echo 'Test Ejecutados con Fallo'
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }

        stage('Generar evidencia'){
            steps
                    {
                        script
                                {
                                    try
                                    {
                                        bat  " rename \"${WORKSPACE}\\target\" serenity_${timestamp}"
                                        echo 'Backup de evidencias realizado con exito'

                                        publishHTML([
                                                allowMissing: false,
                                                alwaysLinkToLastBuild: true,
                                                keepAll: true,
                                                reportDir: "${WORKSPACE}//serenity_${timestamp}",
                                                reportFiles: 'index.html',
                                                reportName: 'Evidencias Automatizacion APIs REST',
                                                reportTitles: 'APIs REST'
                                        ])
                                        echo 'Reporte Html realizado con exito'
                                    }
                                    catch(e)
                                    {
                                        echo 'No se realizo el Backup de evidencias'
                                        publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: "${WORKSPACE}//target/serenity_${timestamp}", reportFiles: 'index.html', reportName: 'Evidencias Automatizacion APIs REST', reportTitles: 'APIs REST'])
                                        echo 'Reporte Html realizado con exito'
                                        currentBuild.result='SUCCESS'
                                    }
                                }
                    }
        }

        stage('Notificar') {
            steps {
                script {
                    if (currentBuild.result == 'UNSTABLE')
                        currentBuild.result = 'FAILURE'

                    if (currentBuild.result == 'SUCCESS')
                        emailext(
                                subject: "NOMBRE FUNCIONALIDAD - EJECUCION EXITOSA ESCENARIOS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                                body: """<p><b style="color:MediumSeaGreen;">EJECUCION EXITOSA:</b> Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
            				<p><b>Para verificar el estado de la ejecucion ingrese a:</b> &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                                to: "${CORREOS}"
                        )
                    if (currentBuild.result == 'FAILURE')
                        emailext(
                                subject: "NOMBRE FUNCIONALIDAD - EJECUCION FALLIDA ESCENARIOS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                                body: """<p><b style="color:red;">EJECUCION FALLIDA:</b> Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
            				<p><b>Para verificar el estado de la ejecucion ingrese a:</b> &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                                to: "${CORREOS}"
                        )
                }
            }
        }
    }
}