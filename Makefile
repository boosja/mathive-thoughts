tailwind-build:
	npx tailwindcss -i ./src/main.css -o ./resources/public/styles.css

tailwind:
	npx tailwindcss -i ./src/main.css -o ./resources/public/styles.css --watch

build:
	clojure -X:build

.PHONY: build tailwind tailwind-build
