def csv = new File(args[0])

csv.eachLine { line -> 
    items = line.split(',')
    println "INSERT INTO xxx VALUES('${items[0]}', '${items[1]}')"
}