import axios, { type AxiosResponse } from "axios";
import { env } from "../application/env";

const apiClient = axios.create({
  baseURL: env.API_BASE_URL,
  headers: {
    'Content-Type': "application/json",
  }
});

const apiRequest = async <T>(
  url: string,
  method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE',
  data?: any): Promise<T> => {
  const response: AxiosResponse<T> = await apiClient({
    method,
    url,
    data
  });

  return response.data;
}

export default apiRequest;
