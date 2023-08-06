# live-commerce

Live commerce platform

## TAG

- live-user-provider

#### We use MySQL to store the tags

- add tag (or operator)

  - 0 0 0 0 1 (current)
  - 1 0 0 0 0 (tag to add)
  - "-------------"
  - 1 0 0 0 0 (or result)

- remove tag (negation & and operator)

  - 1 0 0 0 1 (current)
  - 1 0 0 0 0 (tag to remove)
  - 0 1 1 1 1 (negation of tag)
  - "-------------"
  - 0 0 0 0 1 (and result)
