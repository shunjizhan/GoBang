test: AI executable 
	# python referee.py 11 ./AI ./AI
	# python referee.py 15 ./AI ./executable
	# python referee.py 11 ./executable ./AI	
	# python referee.py 5 ./AI ./AI
	# python referee.py 5 ./AI ./executable
	# python referee.py 5 ./executable ./AI
	# python referee.py 8 ./AI ./AI
	# python referee.py 8 ./AI ./executable
	# python referee.py 8 ./executable ./AI
	# python referee.py 15 ./AI ./AI
	python referee.py 15 ./AI ./executable
	# python referee.py 15 ./executable ./AI
	# python referee.py 26 ./AI ./AI
	# python referee.py 26 ./AI ./executable
	# python referee.py 26 ./executable ./AI

run: AI
	ant compile
	./AI -n 11