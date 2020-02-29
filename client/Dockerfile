FROM node:8.7.0-alpine
RUN mkdir -p /usr/app/multiprotocolapi/client
WORKDIR /usr/app/multiprotocolapi/client
COPY package.json /usr/app/multiprotocolapi/client
COPY package-lock.json /usr/app/multiprotocolapi/client
RUN npm install
COPY . /usr/app/multiprotocolapi/client
CMD ["node", "amqp_receiver.js"]
CMD ["npm", "start"]