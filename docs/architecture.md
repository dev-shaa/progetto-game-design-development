# GameObject

float x
float y
float angle

# Components

## Input

receive(Input input);

## Physics

onCollisionEnter()
onCollisionExit()

## Rendering

draw(Graphics graphics)

## Logic

update(float deltaTime)

## Step to execute every update

1. liquidfun physics update
2. call all "onCollisionEnter"
3. call all "onCollisionExit"
4. call all "receive"
5. call all "update"
6. clear screen
7. call all "draw"
