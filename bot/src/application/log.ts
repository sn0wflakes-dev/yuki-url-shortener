import winston from "winston";

export const log = winston.createLogger({
  level: "debug",
  format: winston.format.json(),
  transports: [ new winston.transports.Console({}) ]
});
