test: Gobang executable 
	# python referee.py 11 ./Gobang ./Gobang
	# python referee.py 15 ./Gobang ./executable
	# python referee.py 11 ./executable ./Gobang	
	# python referee.py 5 ./Gobang ./Gobang
	# python referee.py 5 ./Gobang ./executable
	# python referee.py 5 ./executable ./Gobang
	# python referee.py 8 ./Gobang ./Gobang
	# python referee.py 8 ./Gobang ./executable
	# python referee.py 8 ./executable ./Gobang
	# python referee.py 15 ./Gobang ./Gobang
	python referee.py 15 ./Gobang ./executable
	# python referee.py 15 ./executable ./Gobang
	# python referee.py 26 ./Gobang ./Gobang
	# python referee.py 26 ./Gobang ./executable
	# python referee.py 26 ./executable ./Gobang

run: Gobang
	ant compile
	./Gobang -n 5