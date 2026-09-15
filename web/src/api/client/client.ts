import axios, { type AxiosResponse } from "axios";

const baseURL = import.meta.env.VITE_API_BASE_URL;

const apiClient = axios.create({
  baseURL: baseURL,
  headers: {
    'Content-Type': "application/json",
  }
});

const apiRequest = async <T>(
  url: string,
  method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE',
  data?: unknown): Promise<T> => {
  const response: AxiosResponse<T> = await apiClient({
    method,
    url,
    data
  });

  return response.data;
}

export default apiRequest;
