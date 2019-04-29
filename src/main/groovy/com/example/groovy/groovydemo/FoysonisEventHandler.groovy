package com.example.groovy.groovydemo


import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.GetObjectRequest
import com.example.groovy.groovydemo.domain.DynamicScript
import com.example.groovy.groovydemo.domain.DynamicScriptService
import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class FoysonisEventHandler implements ApplicationContextAware {

    private ApplicationContext applicationContext

    @Value('${foysonis.script.local.path}')
    private String sourcePath

    @Value('${foysonis.script.s3.bucket}')
    private String s3Bucket

    @Autowired
    private DynamicScriptService dynamicScriptService

    @Autowired
    private DynamicScriptLoader dynamicScriptLoader

    @EventListener()
    public void handleEvent(FoysonisEvent event) {

        println "Event Received..."
        println "Event Type: " + event.getEventType()
        println "Event input: " + event.getSource()

        List<DynamicScript> scripts = dynamicScriptService.get(event.getCompanyId(), event.getEventType())
        for(DynamicScript script: scripts) {
            try {
                File sourceFile = getSourceFile(script)

                def gcl = dynamicScriptLoader.getClassLoader()
                def clazz = gcl.parseClass(sourceFile)
                def eventProcessor = clazz.newInstance()
                AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory()
                factory.autowireBean(eventProcessor)
                println "Executing script: " + clazz.getName()
                eventProcessor.process(event.getSource())
            } catch(Exception e) {
                e.printStackTrace()
            }
        }

    }

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext
    }

    private File getSourceFile(DynamicScript dynamicScript) throws Exception {
        File file = new File(sourcePath + File.separator + dynamicScript.companyId + File.separator + dynamicScript.checksum)
        if (file.isFile()) {
            return file
        } else {
            downloadFile(dynamicScript, file)
            return file
        }
    }

    private void downloadFile(DynamicScript dynamicScript, File file) throws Exception {
        println "Downloading File...."
        try {
            AmazonS3Client s3 = new AmazonS3Client()
            s3.getObject(new GetObjectRequest(s3Bucket, dynamicScript.companyId+File.separator+dynamicScript.filename), file)
            if (!file.exists() && !file.canRead()) {
               throw new Exception("Script could not be downloaded.")
            }
        } catch(Exception e) {
            throw e
        }
    }

}
