@Grab("org.codehaus.groovy:groovy-json")
import groovy.json.*

class Calculation{
    def static input = []
    def static inputClone = []
	
	/**
	 * Main method
	 * @param args
	 */
    public static void main(String [] args) {
        def count = 0
        def YorN = 'Y'
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in))
		try {
	        while(count < 6) {
	            count++
	            println('Enter valid number ' + count + ':')
	            def num = br.readLine()
	            input.add(num.toInteger())
	        }
	        inputClone = input.collect()
	        
	        while(YorN.equalsIgnoreCase('Y')) {
	            println('''
	            a) Perform subtraction and show output on screen comma separated.
	            b) Perform multiplication and store result in a JSON file.
	            c) Pick randomly a number and show it on screen.
	            d) Print sorted (highest to lowest) array/list numbers.
	            e) Print sorted (lowest to highest) array/list numbers.
	            ''')
	            println('Choose an option from above: ')
	            def opt = br.readLine()
	            switch(opt){
	                case 'a':
						subtraction()
	                    break
	                case 'b':
	                    multiplyData()
	                    break
	                case 'c':
	                    pickRandomNum()
	                    break
	                case 'd':
						descending()
	                    println(getInputClone())
	                    break
	                case 'e':
						ascending()
	                    println(getInputClone())
	                    break
	                default:
	                    println('Please provide valid input')
	                    break
	            }
	            println('Do you wish to continue(Y/N): ')
	            YorN = br.readLine();
	        }
		} catch(Exception e) {
			println(e.getMessage())
		} finally {
			println("\nIf you wish to perform this operations again, please run the Calculation script.\nThank you!\n");
			br.close()
		}
    }

	/**
	 * Subtraction
	 */
    public static void subtraction(){
		descending()
		def List res = []
		for(int i=1; i < inputClone.size(); i++) {
			res.add(inputClone[i-1] - inputClone[i])
		}
		println(res)
    }

	/**
	 * Random elements
	 */
    public static void pickRandomNum(){
        def random = new Random()
        println(input.get(random.nextInt(input.size())))
    }

	/**
	 * Descending
	 */
    public static void descending(){
        ascending()
        inputClone.reverse(true)
    }

	/**
	 * Ascending
	 */
    public static void ascending(){
        inputClone.sort()
    }

	/**
	 * Multiply elements and print JSON Data
	 */
    public static void multiplyData(){
        def mul = 1
        for (element in input) {
            mul = mul*element
        }
        def json = constructJsonData()
        json.put('Multiplication', mul)
        def output =  new JsonBuilder(json)
		println('JSON Body of Elements: \n' +output.toPrettyString())
    }

	/**
	 * Constructs initial Data for JSON and returns Map
	 * @return dataMap
	 */
    public static Map constructJsonData(){
		def dataMap = [:]
        for (int i=1; i <= input.size(); i++) {
			dataMap.put('InputNumber'+ i , input.get(i-1))
        }
        return dataMap
    }
}