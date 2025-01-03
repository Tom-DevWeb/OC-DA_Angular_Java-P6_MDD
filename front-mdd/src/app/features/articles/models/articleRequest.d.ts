export interface ArticleRequest {
  title: string
  content: string
  author: {
    id: number
  }
  theme: {
    id: number
  }
}
