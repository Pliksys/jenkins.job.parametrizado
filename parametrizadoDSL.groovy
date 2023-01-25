job('ejemplo2-job-DSL') {
  description('Job DSL de ejemplo para el curso de Jenkins')
  scm {
    git('https://github.com/Pliksys/jenkins.job.parametrizado.git', 'main') {node ->
      node / gitConfigName('PLiksys')
      node / gitConfigEmail('gvidalmorata@gmail.com')
    }
  }
  parameters {
    	stringParam('nombre', defaultValue = 'Genis', description = 'Parametro de cadena para el job Booleano')
    	choiceParam('planeta', ['Mercurio', 'Venus', 'Tierra', 'Marte', 'Jupiter', 'Saturno', 'Urano', 'Neptuno'])
    	booleanParam('agente', false)
  }
  triggers{
  	cron('H/7 * * * *')
    githubPush()
  }
  steps{
  	shell("bash jobscript.sh")
  }
  publishers{
    mailer('gvidalmorata@gmail.com', true, true)
    slackNotifier {
      notifyAborted(true)
      notifyEveryFailure(true)
      notifyNotBuilt(false)
      notifyUnstable(false)
      notifyBackToNormal(true)
      notifySuccess(false)
      notifyRepeatedFailure(false)
      startNotification(false)
      includeTestSummary(false)
      includeCustomMessage(false)
      customMessage(null)
      sendAs(null)
      commitInfoChoice('NONE')
      teamDomain(null)
      authToken(null)
    }
  }
}
