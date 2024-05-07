FROM node:lts-alpine3.19

WORKDIR /app

COPY package.json .

RUN npm install

COPY . .

EXPOSE 5173

ENTRYPOINT ["npm","run","host"]
