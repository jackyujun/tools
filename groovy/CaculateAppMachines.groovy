@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2')

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovyx.net.http.*

/**
 * 
 * @author jack.yuj jack.yuj@alibaba-inc.com
 */
class CaculateAppMachines {
    def static main(args){
        def appNames = usAppNames
        if(args.size() > 0){
            if(args[0] == 'hz'){
                appNames = hzAppNames
            }
        }
        println("IDC : " + (hzAppNames == appNames ? 'hz': 'us'))
        appNames.each {
            def numbers = getCabinetOfMathine(it).size()
            //            if (numbers > 0) {
            //                numbers -= 1
            //            }
            println "AppName:[${it}] The numbers of server:[${numbers}]"
        }
    }

    def static getCabinetOfMathine(hostName){
        def http = new HTTPBuilder( 'http://localhost' )
        def resultList = [];
        http.request(GET,TEXT) {
            uri.path = '/getvalue.php'
            uri.query = [RaString:hostName, RaParam1:'-o']

            // response handler for a success response code:
            response.success = { resp, json ->
                //println json.name() //== 'HTML'
                def value = json?.text
                value?.split(' ').each {  resultList << it }

            }

            // handler for any failure status code:
            response.failure = { resp ->  println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"  }
        }

        return resultList
    }

    def static hzAppNames = [
        'wholesale'
    ]

    def static usAppNames = [
        'wholesale'
    ]
}
