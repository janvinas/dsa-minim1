# Mínim 1

Jan Viñas Corella

## Part 1

### 1

**MapElement** 
* ElementType type
* int horizontal
* int vertical
* TreeMap<LocalDateTime, User> usersSeen

**User**
* String id
* String name
* String surname
* String email
* LocalDate birthday
* TreeMap<LocalDateTime, MapElement> visitedElements

**ElementManagerImpl**
* ArrayList\<MapElement\> elements
* ArrayList\<User\> users