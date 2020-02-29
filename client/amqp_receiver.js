var amqp = require("amqplib/callback_api");

amqp.connect("amqp://localhost", function(error0, connection) {
  if (error0) {
    throw error0;
  }
  connection.createChannel(function(error1, channel) {
    if (error1) {
      throw error1;
    }

    var queue = "new-car-queue";

    channel.assertQueue(queue, {
      durable: true
    });

    console.log(" [*] Waiting for messages in %s. To exit press CTRL+C", queue);

    channel.consume(
      queue,
      function(msg) {
        console.log(
          "Received message in queue %s: %s",
          queue,
          msg.content.toString()
        );
      },
      {
        noAck: true
      }
    );
  });
});
