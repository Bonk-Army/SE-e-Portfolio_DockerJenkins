import jenkins.model.*
import hudson.security.*

def env = System.getenv()

def jenkins = Jenkins.getInstance()
jenkins.setSecurityRealm(new HudsonPrivateSecurityRealm(false))
jenkins.setAuthorizationStrategy(new GlobalMatrixAuthorizationStrategy())
j.setCrumbIssuer(null)
j.save()

def user = jenkins.getSecurityRealm().createAccount(env.JENKINS_USER, env.JENKINS_PASS)
user.save()

jenkins.getAuthorizationStrategy().add(Jenkins.ADMINISTER, env.JENKINS_USER)
jenkins.save()

if(!jenkins.instance.isQuietingDown()) {
    def j = jenkins.instance
    if(j.getCrumbIssuer() != null) {
        j.setCrumbIssuer(null)
        j.save()
    }
}
