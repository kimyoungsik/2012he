//
//  BIDViewController.m
//  chat3
//
//  Created by developer on 12. 1. 5..
//  Copyright (c) 2012 HeadFlow All rights reserved.
//

#import "BIDViewController.h"
#import <sys/types.h>
#import <sys/socket.h>
#import <netinet/in.h>
#import <netdb.h>

void CFSockCallBack (
                     CFSocketRef s,
                     CFSocketCallBackType callbackType,
                     CFDataRef address,
                     const void *data,
                     void *info
                     ) 
{
    BIDViewController * self = (__bridge id)info;
    
    if(callbackType == kCFSocketReadCallBack) {
      
        //receive data from server
        char buf[600] = {'\0'};        
        int sock = CFSocketGetNative(s);
        recv(sock, &buf, 600, 0);

        NSString *sData = [NSString stringWithUTF8String:(const char*)buf+2 ];
        
        if(sData.length > 0 && sData.length < 201)
        {
            self.showView.text = sData;
            [self.showView scrollRangeToVisible:NSMakeRange([self.showView.text length], 0)];
            self.tempstring = [NSString stringWithString:sData];
        }
        else
        {
            self.showView.text = self.tempstring;
        }

        
        //send data to server
        const UInt8 sendbuf[1] = {0};   
        CFDataRef dt = CFDataCreate(NULL, sendbuf, 1);
        CFSocketSendData(s, NULL, dt, 1);   
        
    }

    if(callbackType == kCFSocketConnectCallBack) {
        
    }
}


@implementation BIDViewController
@synthesize showView;

@synthesize ipsend;
@synthesize portsend;
@synthesize tempstring;

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}
#pragma mark - 
#pragma mark View Lifecycle 
- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.  
    [self startSocket];
    
}


- (void) startSocket{
    
    
    const CFSocketContext socketContext = { 0, self, NULL, NULL, NULL };
    
    
    CFSocketRef ref = CFSocketCreate(kCFAllocatorDefault, PF_INET, SOCK_STREAM, 0, 
                                     kCFSocketReadCallBack|kCFSocketConnectCallBack,
                                     CFSockCallBack,&socketContext );
    struct sockaddr_in theName;
    struct hostent *hp;
    
    NSString *nsipsend =  self.ipsend;
    NSString *nsportsend = self.portsend;
    
    int iPort = [nsportsend intValue];
    theName.sin_port = htons(iPort);
    theName.sin_family = AF_INET;
    
    const char* ipAddress = [nsipsend cStringUsingEncoding:NSUTF8StringEncoding]; 
    
    hp = gethostbyname(ipAddress);
    if( hp == NULL ) {
        return;
    }
    memcpy( &theName.sin_addr.s_addr, hp->h_addr_list[0], hp->h_length );
    
    CFDataRef addressData = CFDataCreate( NULL, &theName, sizeof( struct sockaddr_in ) );
    CFSocketConnectToAddress(ref, addressData, 30);
    
    CFRunLoopSourceRef FrameRunLoopSource = CFSocketCreateRunLoopSource(NULL, ref , 0);
    CFRunLoopAddSource(CFRunLoopGetCurrent(), FrameRunLoopSource, kCFRunLoopCommonModes);  
}


- (void)viewDidUnload
{
    
    [self setShowView:nil];
    [super viewDidUnload];
    
    
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

- (void)willAnimateRotationToInterfaceOrientation:(UIInterfaceOrientation)
interfaceOrientation duration:(NSTimeInterval)duration {
    
    if (UIInterfaceOrientationIsPortrait(interfaceOrientation)) { 
        
        showView.frame = CGRectMake(0, 0, 320, 416);
        
    } 
    else
    {
        showView.frame = CGRectMake(0, 0, 480, 270);
    }
}


- (void)dealloc {
    [showView release];
    
    [super dealloc];
}

@end
