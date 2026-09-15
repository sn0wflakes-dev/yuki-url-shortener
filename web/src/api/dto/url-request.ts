export interface UrlRequest {
  longUrl: string;
  alias: string | null;
}

export interface UrlResponse {
  url: string;
  shortUrl: string;
}
