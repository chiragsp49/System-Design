package main

import (
	"errors"
	"fmt"
)

func main() {
	var printValue string = "Hello World"
	printMe(printValue)

	var num int = 11
	var dem int = 2

	var quoteint, remainder, err = intDivision(num, dem)
	if err != nil {
		fmt.Println("Error ", err)
	} else if remainder == 0 {
		fmt.Printf("The result of the integer division is %v", quoteint)
	} else {
		fmt.Printf("The result of the integer division is %v and remainder is %v", quoteint, remainder)
	}
	fmt.Print(quoteint, remainder)

	//Arrays
	var intArr [3]int32 = [3]int32{1, 2, 3}
	intArr2 := [3]int32{1, 2, 3}
	fmt.Println(intArr)
	fmt.Println(intArr2)

	var intSlice []int32 = []int32{1, 2, 3}

	intSlice = append(intSlice, 7)
	fmt.Println(intSlice)

	var intSlice2 []int32 = []int32{8, 9}
	intSlice = append(intSlice, intSlice2...)
	fmt.Println(intSlice)

	var intSlice3 []int32 = make([]int32, 3, 10)
	fmt.Println(intSlice3)

	//Maps
	var myMap map[string]uint8 = make(map[string]uint8)
	fmt.Println(myMap)

	var myMap2 = map[string]uint8{"Chirag": 32, "Nidhi": 29}
	fmt.Println(myMap2["Chirag"])
	fmt.Println(myMap2["Amrutha"])

	var age, ok = myMap2["Amrutha"]
	if ok {
		fmt.Println(age)
	} else {
		fmt.Println("Does not exist")
	}

	for name := range myMap2 {
		fmt.Println(name)
	}

	for name, age := range myMap2 {
		fmt.Println(name, age)
	}

	for i, v := range intArr2 {
		fmt.Printf("Index: %v, Value %v\n", i, v)
	}

	for i := 0; i < 10; i++ {
		fmt.Println(i)
	}

	//Strings
	var myString string = "resume"
	fmt.Println(myString)

	for i, v := range myString {
		fmt.Println(i, v)
	}

	var myRune rune = 'a'
	fmt.Println(myRune)
}

func printMe(printVal string) {
	fmt.Println(printVal)
}

func intDivision(num int, dem int) (int, int, error) {
	var err error
	if dem == 0 {
		err = errors.New("CANNOT DIVIDE BY ZERO")
		return 0, 0, err
	}

	var quoteint int = num / dem
	var remainder int = num % dem
	return quoteint, remainder, err
}
