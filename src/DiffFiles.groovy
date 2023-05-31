import java.io.FileNotFoundException
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.regex.Pattern
import java.util.regex.Matcher

/**
 * Test Data needs to be created before executing this
 *
 */
class DiffFiles{

    def static File added = new File('added.txt')
    def static File removed = new File('removed.txt')
    def static File addedPath = new File("deployPackage/added/")
    def static File removePath = new File("deployPackage/removed/")

	/**
	 * Main method
	 * @param args
	 */
    public static void main(String [] args){
        def path="/mnt/e/Practise_Code/TestGroovy/src/testfile.txt"
        added.delete()
        added.createNewFile()
        removed.delete()
        removed.createNewFile()
        addedPath.mkdirs()
        removePath.mkdirs()
        DiffFiles d = new DiffFiles()
        d.readLineByLine(path);
    }

	/**
	 * Read data from text file
	 * @param path
	 */
    public void readLineByLine(path){
        File file = new File(path)
        def lines = file.readLines()
		try {
	        for(line in lines) {
	            def p1 = /[A-Z]{1}\s/
	            def p2 = /(?![A-Z]{1})\s.*$/
	            def matcher1 = line.toString() =~ p1
	            def matcher2 = line.toString() =~ p2
	            def matchList = []
	            while (matcher1.find()) {
	                matchList.add(matcher1.group().trim());
	            }
	            while (matcher2.find()) {
	                matchList.add(matcher2.group().trim());
	            }
	            if(matchList.size() == 2) {
	                File sfile = new File(matchList[1].toString())
	                separateFiles(matchList[0].toString(), sfile)
	            } else {
	                println('ERROR: Invalid diff file. Please Check.')
	            }
	        }
		} catch(Exception e) {
			println(e.getMessage())
		}
    }

	/**
	 * Process the files are per their git status
	 * @param status
	 * @param file
	 */
    public void separateFiles(String status, File file) {
        switch(status) {
            case 'M':
            case 'A':
                added.append(file.getName() + '\n')
				def filepath = file.getAbsolutePath()
				def addPathT = addedPath.getAbsolutePath()
				def process
				try {
					process = "cp -pr $filepath $addPathT".execute()
				} catch(Exception e) {
					println "Output: " + process.text
					println "Exit code: " + process.exitValue()
				}
                break
            case 'R':
            case 'D':
                removed.append(file.getName() + '\n')
				def filepath = file.getAbsolutePath()
				def removePathT = removePath.getAbsolutePath() + '/' + file.getName()
				def process
				try {
					process = "mv $filepath $removePathT".execute()
				} catch(Exception e) {
					println "Output: " + process.text
					println "Exit code: " + process.exitValue()
				}
                break
            default:
				println('ERROR: Invalid Entry for '+ file.getName() +'. Please check Diff file.')
                break
        }
    }
}