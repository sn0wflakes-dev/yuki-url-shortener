export interface WebResponse<T>{
  messageHeader: MessageHeader;
  data: T;
  Errors: Errors;
}

interface MessageHeader {
  requestId: string;
  timestamp: string;
  message: string;
  path: string;
}

interface Errors {
  errorCode: string;
  errorMessage: string;
  details: Details[];
}

interface Details {
  field: string;
  message: string;
}
